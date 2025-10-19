package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.entities.Article;
import dev.justinmartz.artificial_news.entities.ArticlePhoto;
import dev.justinmartz.artificial_news.exceptions.ArticleNotCreatedException;
import dev.justinmartz.artificial_news.exceptions.ArticleNotFoundException;
import dev.justinmartz.artificial_news.repositories.ArticleRepository;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.imageio.ImageIO;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Value("${image.upload.dir}")
    private String uploadDirectory;

    private final ArticleRepository articleRepository;
    private final AiService aiService;

    public ArticleServiceImpl(ArticleRepository articleRepository, AiService aiService) {
        this.articleRepository = articleRepository;
        this.aiService = aiService;
    }

    @Override
    public Article createArticle() {
        String topic = aiService.generateTopic();
        Map<String, String> articleMap = aiService.generateText(topic);

        CompletableFuture<ImageResponse> authorPhotoFuture =
                aiService.generateAuthorImageAsync(articleMap.get("author"));
        CompletableFuture<ImageResponse> articlePhotoFuture =
                aiService.generateArticleImageAsync(articleMap.get("headline"));
        CompletableFuture.allOf(authorPhotoFuture, articlePhotoFuture).join();

        ImageResponse authorPhotoResponse = authorPhotoFuture.join();
        ImageResponse articlePhotoResponse = articlePhotoFuture.join();

        String authorPhotoFilename = saveAuthorPhoto(authorPhotoResponse, articleMap.get("author"));
        String articlePhotoFilename =
                saveArticlePhoto(articlePhotoResponse, articleMap.get("headline"));

        Article article =
                buildArticleFromArticleMap(articleMap, authorPhotoFilename, articlePhotoFilename);

        if (article.isFullyInitialized()) {
            articleRepository.save(article);
            return article;
        } else {
            throw new ArticleNotCreatedException(
                    "createArticle(), article incomplete", new RuntimeException());
        }
    }

    @Override
    public Article getArticleById(UUID id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @Override
    public Page<Article> getPagedArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);

        return articles;
    }

    private String saveAuthorPhoto(ImageResponse authorImage, String author) {
        // Produces something like Avery-Williams-1721680383905.png
        String filename =
                author.replaceAll("\\s+", "-") + "-" + System.currentTimeMillis() + ".png";

        String url = authorImage.getResult().getOutput().getUrl();
        URL imageUrl = null;
        try {
            imageUrl = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            ImageIO.write(image, "png", new File(uploadDirectory, filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filename;
    }

    private String formatUTCtoDateline(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL d, yyyy • h:mm a");
        return localDateTime.format(formatter);
    }

    private String saveArticlePhoto(ImageResponse articleImage, String headline) {
        //     Produces something like
        //     Undead-Fun-Zombie-Apocalypse-Marathon-Takes-Over-New-York-City-1721686055684.png

        ArticlePhoto articlePhoto = new ArticlePhoto();
        String filename =
                headline.replaceAll("[\\s,:]+", "-") + "-" + System.currentTimeMillis() + ".png";

        byte[] imageBytes =
                Base64.getDecoder().decode(articleImage.getResult().getOutput().getB64Json());

        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);
            if (image == null) {
                throw new ArticleNotCreatedException(
                        "fetchAndSaveArticlePhoto()", new IOException());
            }

            File outputFile = new File(uploadDirectory, filename);
            ImageIO.write(image, "png", outputFile);

            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Article buildArticleFromArticleMap(
            Map<String, String> articleMap,
            String authorPhotoFilename,
            String articlePhotoFilename) {
        ArticlePhoto articlePhoto = new ArticlePhoto();
        articlePhoto.setCaption(articleMap.get("articlePhotoCaption"));
        articlePhoto.setPhotographer(articleMap.get("articlePhotoPhotographer"));
        articlePhoto.setFilename(articlePhotoFilename);

        Article article = new Article();
        article.setHeadline(articleMap.get("headline"));
        article.setAuthor(articleMap.get("author"));
        article.setArticleBody(articleMap.get("articleBody"));
        article.setAuthorPhoto(authorPhotoFilename);
        article.setArticlePhoto(articlePhoto);
        article.setCreatedAt(LocalDateTime.now());
        article.setDateline(formatUTCtoDateline(LocalDateTime.now()));

        return article;
    }
}
