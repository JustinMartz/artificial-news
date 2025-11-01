package dev.justinmartz.artificial_news.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.justinmartz.artificial_news.entities.ArticleEntity;
import dev.justinmartz.artificial_news.exceptions.ArticleNotCreatedException;
import dev.justinmartz.artificial_news.exceptions.ArticleNotFoundException;
import dev.justinmartz.artificial_news.models.ArticleDto;
import dev.justinmartz.artificial_news.models.ArticlePhotoDto;
import dev.justinmartz.artificial_news.repositories.ArticleRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {

    @Mock private ArticleRepository mockArticleRepository;

    @Mock private OpenAiServiceImpl mockAiService;

    @Mock private ImageStorageService mockImageStorageService;

    @InjectMocks private ArticleServiceImpl articleService;

    private static final String EXCEPTION_MESSAGE =
            "Error creating article in createArticle(): Cannot save incomplete article.";
    private static final String ARTICLE_NOT_FOUND_EXCEPTION_MESSAGE =
            "Could not find article with id: ";

    @Test
    void givenCreateArticle_whenCalled_thenCallsAiServiceMethods() {
        String testTopic = UUID.randomUUID().toString();
        ArticleDto testArticleDto = buildCompleteArticleDto(true);
        ImageResponse mockImageResponse = buildTestImageResponse();

        when(mockAiService.generateTopic()).thenReturn(testTopic);
        when(mockAiService.generateText(testTopic)).thenReturn(testArticleDto);
        when(mockAiService.generateAuthorImageAsync(testArticleDto.getAuthor()))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));
        when(mockAiService.generateArticleImageAsync(testArticleDto.getHeadline()))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));
        when(mockImageStorageService.saveAuthorPhoto(any(), anyString()))
                .thenReturn(UUID.randomUUID().toString());
        when(mockImageStorageService.saveArticlePhoto(any(), anyString()))
                .thenReturn(new ArticlePhotoDto());

        ArticleEntity articleEntity = articleService.createArticle();

        verify(mockAiService).generateTopic();
        verify(mockAiService).generateText(testTopic);
        verify(mockAiService).generateAuthorImageAsync(testArticleDto.getAuthor());
        verify(mockAiService).generateArticleImageAsync(testArticleDto.getHeadline());
        assertNotNull(articleEntity);
        assertEquals(testArticleDto.getHeadline(), articleEntity.getHeadline());
        assertEquals(testArticleDto.getAuthor(), articleEntity.getAuthor());
        assertEquals(testArticleDto.getArticleBody(), articleEntity.getArticleBody());
        assertEquals(
                testArticleDto.getArticlePhotoCaption(),
                articleEntity.getArticlePhoto().getCaption());
        assertEquals(
                testArticleDto.getArticlePhotoPhotographer(),
                articleEntity.getArticlePhoto().getPhotographer());
    }

    @Test
    void givenCreateArticle_whenCalled_thenCallsImageStorageServiceMethods() {
        String authorPhotoFilename = UUID.randomUUID().toString();
        ArticlePhotoDto testArticlePhotoDto =
                new ArticlePhotoDto()
                        .setFullsize(UUID.randomUUID().toString())
                        .setThumbnail(UUID.randomUUID().toString());
        ArticleDto testArticleDto = buildCompleteArticleDto(true);
        ImageResponse mockAuthorImageResponse = buildTestImageResponse();
        ImageResponse mockArticleImageResponse = buildTestImageResponse();

        when(mockAiService.generateTopic()).thenReturn(UUID.randomUUID().toString());
        when(mockAiService.generateText(any())).thenReturn(testArticleDto);
        when(mockAiService.generateAuthorImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockAuthorImageResponse));
        when(mockAiService.generateArticleImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockArticleImageResponse));
        when(mockImageStorageService.saveAuthorPhoto(
                        eq(mockAuthorImageResponse), eq(testArticleDto.getAuthor())))
                .thenReturn(authorPhotoFilename);
        when(mockImageStorageService.saveArticlePhoto(
                        eq(mockArticleImageResponse), eq(testArticleDto.getHeadline())))
                .thenReturn(testArticlePhotoDto);

        ArticleEntity articleEntity = articleService.createArticle();

        verify(mockImageStorageService)
                .saveArticlePhoto(mockArticleImageResponse, testArticleDto.getHeadline());
        verify(mockImageStorageService)
                .saveAuthorPhoto(mockAuthorImageResponse, testArticleDto.getAuthor());
        assertNotNull(articleEntity);
        assertEquals(
                testArticlePhotoDto.getFullsize(), articleEntity.getArticlePhoto().getFullsize());
        assertEquals(
                testArticlePhotoDto.getThumbnail(), articleEntity.getArticlePhoto().getThumbnail());
        assertEquals(authorPhotoFilename, articleEntity.getAuthorPhoto());
    }

    @Test
    void givenCreateArticle_whenArticleIsFullyInitialized_thenSavesArticle() {
        ArticleDto testArticleDto = buildCompleteArticleDto(true);
        ImageResponse mockImageResponse = buildTestImageResponse();

        when(mockAiService.generateTopic()).thenReturn(UUID.randomUUID().toString());
        when(mockAiService.generateText(any())).thenReturn(testArticleDto);
        when(mockAiService.generateAuthorImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));
        when(mockAiService.generateArticleImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));
        when(mockImageStorageService.saveAuthorPhoto(any(), anyString()))
                .thenReturn(UUID.randomUUID().toString());
        when(mockImageStorageService.saveArticlePhoto(any(), anyString()))
                .thenReturn(new ArticlePhotoDto());

        ArticleEntity articleEntity = articleService.createArticle();

        verify(mockArticleRepository).save(articleEntity);
        assertNotNull(articleEntity);
        assertTrue(articleEntity.isFullyInitialized());
    }

    @Test
    void givenCreateArticle_whenArticleIsNotFullyInitialized_thenThrowsException() {
        ArticleDto testArticleDto = buildCompleteArticleDto(false);

        when(mockAiService.generateTopic()).thenReturn(UUID.randomUUID().toString());
        when(mockAiService.generateText(any())).thenReturn(testArticleDto);
        when(mockAiService.generateAuthorImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(null));
        when(mockAiService.generateArticleImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(null));
        when(mockImageStorageService.saveAuthorPhoto(null, null))
                .thenReturn(UUID.randomUUID().toString());
        when(mockImageStorageService.saveArticlePhoto(any(), anyString()))
                .thenReturn(new ArticlePhotoDto());

        ArticleNotCreatedException thrown =
                assertThrows(
                        ArticleNotCreatedException.class, () -> articleService.createArticle());

        assertTrue(thrown.getMessage().contains(EXCEPTION_MESSAGE));
    }

    @Test
    void givenGetArticleById_whenArticleFound_thenReturnsArticle() {
        UUID uuid = UUID.randomUUID();
        ArticleEntity mockArticleEntity = new ArticleEntity();

        when(mockArticleRepository.findById(uuid)).thenReturn(Optional.of(mockArticleEntity));

        ArticleEntity articleEntity = articleService.getArticleById(uuid);

        verify(mockArticleRepository).findById(uuid);
        assertNotNull(articleEntity);
    }

    @Test
    void givenGetArticleById_whenArticleNotFound_thenThrowsException() {
        UUID uuid = UUID.randomUUID();

        ArticleNotFoundException thrown =
                assertThrows(
                        ArticleNotFoundException.class, () -> articleService.getArticleById(uuid));

        verify(mockArticleRepository).findById(uuid);
        assertEquals(ARTICLE_NOT_FOUND_EXCEPTION_MESSAGE + uuid.toString(), thrown.getMessage());
    }

    @Test
    void givenGetPagedArticles_whenCalled_thenGetsPagedArticlesFromRepository() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ArticleEntity> pagedArticles = buildPagedArticles();

        when(mockArticleRepository.findAll(pageable)).thenReturn(pagedArticles);

        Page<ArticleEntity> articles = articleService.getPagedArticles(pageable);

        verify(mockArticleRepository).findAll(pageable);
        assertSame(articles, pagedArticles);
    }

    private ArticleDto buildCompleteArticleDto(boolean isComplete) {
        ArticleDto articleDto = new ArticleDto();
        articleDto
                .setHeadline(UUID.randomUUID().toString())
                .setAuthor(isComplete ? UUID.randomUUID().toString() : null)
                .setArticleBody(UUID.randomUUID().toString())
                .setArticlePhotoCaption(UUID.randomUUID().toString())
                .setArticlePhotoPhotographer(UUID.randomUUID().toString());

        return articleDto;
    }

    private ImageResponse buildTestImageResponse() {
        ImageGeneration generation = mock(ImageGeneration.class);

        return new ImageResponse(List.of(generation));
    }

    private Page<ArticleEntity> buildPagedArticles() {
        List<ArticleEntity> articleEntities = new ArrayList<>();
        articleEntities.add(new ArticleEntity().setAuthor(UUID.randomUUID().toString()));
        articleEntities.add(new ArticleEntity().setAuthor(UUID.randomUUID().toString()));

        return new PageImpl<>(articleEntities);
    }
}
