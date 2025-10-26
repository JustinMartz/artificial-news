package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.entities.Article;
import dev.justinmartz.artificial_news.entities.ArticlePhoto;
import dev.justinmartz.artificial_news.exceptions.ArticleNotCreatedException;
import dev.justinmartz.artificial_news.exceptions.ArticleNotFoundException;
import dev.justinmartz.artificial_news.models.ArticlePhotoDto;
import dev.justinmartz.artificial_news.repositories.ArticleRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.springframework.ai.image.ImageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final AiService aiService;
    private final ImageStorageService imageStorageService;

    public ArticleServiceImpl(
            ArticleRepository articleRepository,
            AiService aiService,
            ImageStorageService imageStorageService) {
        this.articleRepository = articleRepository;
        this.aiService = aiService;
        this.imageStorageService = imageStorageService;
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

        String authorPhotoFilename =
                imageStorageService.saveAuthorPhoto(authorPhotoResponse, articleMap.get("author"));
        ArticlePhotoDto articlePhotoDto =
                imageStorageService.saveArticlePhoto(
                        articlePhotoResponse, articleMap.get("headline"));

        Article article =
                buildArticleFromArticleMap(articleMap, authorPhotoFilename, articlePhotoDto);

        if (article.isFullyInitialized()) {
            articleRepository.save(article);
            return article;
        } else {
            throw new ArticleNotCreatedException(
                    "createArticle(): Cannot save incomplete article.", new RuntimeException());
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

    private Article buildArticleFromArticleMap(
            Map<String, String> articleMap,
            String authorPhotoFilename,
            ArticlePhotoDto articlePhotoDto) {
        ArticlePhoto articlePhoto = new ArticlePhoto();
        articlePhoto.setCaption(
                Objects.isNull(articleMap.get("articlePhotoCaption"))
                        ? null
                        : articleMap.get("articlePhotoCaption"));
        articlePhoto.setPhotographer(
                Objects.isNull(articleMap.get("articlePhotoPhotographer"))
                        ? null
                        : articleMap.get("articlePhotoPhotographer"));
        articlePhoto.setFullsize(articlePhotoDto.getFullsize());
        articlePhoto.setThumbnail(articlePhotoDto.getThumbnail());

        Article article = new Article();
        article.setHeadline(
                Objects.isNull(articleMap.get("headline")) ? null : articleMap.get("headline"));
        article.setAuthor(
                Objects.isNull(articleMap.get("author")) ? null : articleMap.get("author"));
        article.setArticleBody(
                Objects.isNull(articleMap.get("articleBody"))
                        ? null
                        : articleMap.get("articleBody"));
        article.setAuthorPhoto(authorPhotoFilename);
        article.setArticlePhoto(articlePhoto);
        article.setCreatedAt(LocalDateTime.now());
        article.setDateline(formatUTCtoDateline(LocalDateTime.now()));

        return article;
    }

    private String formatUTCtoDateline(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL d, yyyy • h:mm a");
        return localDateTime.format(formatter);
    }
}
