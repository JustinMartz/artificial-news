package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.entities.ArticleEntity;
import dev.justinmartz.artificial_news.entities.ArticlePhoto;
import dev.justinmartz.artificial_news.exceptions.ArticleNotCreatedException;
import dev.justinmartz.artificial_news.exceptions.ArticleNotFoundException;
import dev.justinmartz.artificial_news.models.ArticleDto;
import dev.justinmartz.artificial_news.models.ArticlePhotoDto;
import dev.justinmartz.artificial_news.repositories.ArticleRepository;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
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

    private static final Integer DURATION_DIVISOR = 1_000_000;

    public ArticleServiceImpl(
            ArticleRepository articleRepository,
            AiService aiService,
            ImageStorageService imageStorageService) {
        this.articleRepository = articleRepository;
        this.aiService = aiService;
        this.imageStorageService = imageStorageService;
    }

    @Override
    public ArticleEntity createArticle() {
        Long startTime = System.nanoTime();

        String topic = aiService.generateTopic();
        ArticleDto articleDto = aiService.generateText(topic);

        CompletableFuture<ImageResponse> authorPhotoFuture =
                aiService.generateAuthorImageAsync(articleDto.getAuthor());
        CompletableFuture<ImageResponse> articlePhotoFuture =
                aiService.generateArticleImageAsync(articleDto.getHeadline());
        CompletableFuture.allOf(authorPhotoFuture, articlePhotoFuture).join();

        ImageResponse authorPhotoResponse = authorPhotoFuture.join();
        ImageResponse articlePhotoResponse = articlePhotoFuture.join();

        articleDto.setAuthorPhotoFilename(
                imageStorageService.saveAuthorPhoto(authorPhotoResponse, articleDto.getAuthor()));
        ArticlePhotoDto articlePhotoDto =
                imageStorageService.saveArticlePhoto(
                        articlePhotoResponse, articleDto.getHeadline());

        Long endTime = System.nanoTime();
        Long durationInMillis = (endTime - startTime) / DURATION_DIVISOR;
        articleDto.setCreationTime(durationInMillis);

        ArticleEntity articleEntity = buildArticleFromDtos(articleDto, articlePhotoDto);

        if (articleEntity.isFullyInitialized()) {
            articleRepository.save(articleEntity);
            return articleEntity;
        } else {
            throw new ArticleNotCreatedException(
                    "createArticle(): Cannot save incomplete article.", new RuntimeException());
        }
    }

    @Override
    public ArticleEntity getArticleById(UUID id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @Override
    public Page<ArticleEntity> getPagedArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    private ArticleEntity buildArticleFromDtos(ArticleDto articleDto, ArticlePhotoDto articlePhotoDto) {
        ArticlePhoto articlePhoto = new ArticlePhoto();
        articlePhoto
                .setCaption(
                        Objects.isNull(articleDto.getArticlePhotoCaption())
                                ? null
                                : articleDto.getArticlePhotoCaption())
                .setPhotographer(
                        Objects.isNull(articleDto.getArticlePhotoPhotographer())
                                ? null
                                : articleDto.getArticlePhotoPhotographer())
                .setFullsize(articlePhotoDto.getFullsize())
                .setThumbnail(articlePhotoDto.getThumbnail());

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setHeadline(
                        Objects.isNull(articleDto.getHeadline()) ? null : articleDto.getHeadline())
                .setAuthor(Objects.isNull(articleDto.getAuthor()) ? null : articleDto.getAuthor())
                .setArticleBody(
                        Objects.isNull(articleDto.getArticleBody())
                                ? null
                                : articleDto.getArticleBody())
                .setAuthorPhoto(articleDto.getAuthorPhotoFilename())
                .setArticlePhoto(articlePhoto)
                .setCreatedAt(OffsetDateTime.now())
                .setDateline(formatUTCtoDateline(OffsetDateTime.now()))
                .setProvider(articleDto.getProvider())
                .setModel(articleDto.getModel());

        return articleEntity;
    }

    private String formatUTCtoDateline(OffsetDateTime offsetDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL d, yyyy • h:mm a");

        return offsetDateTime.format(formatter);
    }
}
