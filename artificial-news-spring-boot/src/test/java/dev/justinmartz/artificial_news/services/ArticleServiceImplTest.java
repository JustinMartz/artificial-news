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

import dev.justinmartz.artificial_news.entities.Article;
import dev.justinmartz.artificial_news.exceptions.ArticleNotCreatedException;
import dev.justinmartz.artificial_news.exceptions.ArticleNotFoundException;
import dev.justinmartz.artificial_news.models.ArticlePhotoDto;
import dev.justinmartz.artificial_news.repositories.ArticleRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void setup() {}

    private static final String ARTICLE_MAP_KEY_HEADLINE = "headline";
    private static final String ARTICLE_MAP_KEY_AUTHOR = "author";
    private static final String ARTICLE_MAP_KEY_ARTICLE_BODY = "articleBody";
    private static final String ARTICLE_MAP_KEY_ARTICLE_PHOTO_CAPTION = "articlePhotoCaption";
    private static final String ARTICLE_MAP_KEY_ARTICLE_PHOTO_PHOTOGRAPHER =
            "articlePhotoPhotographer";
    private static final String EXCEPTION_MESSAGE =
            "Error creating article in createArticle(): Cannot save incomplete article.";
    private static final String ARTICLE_NOT_FOUND_EXCEPTION_MESSAGE =
            "Could not find article with id: ";

    @Test
    void givenCreateArticle_whenCalled_thenCallsAiServiceMethods() {
        String testTopic = UUID.randomUUID().toString();
        Map<String, String> testArticleMap = buildCompleteArticleMap(true);
        ImageResponse mockImageResponse = buildTestImageResponse();

        when(mockAiService.generateTopic()).thenReturn(testTopic);
        when(mockAiService.generateText(testTopic)).thenReturn(testArticleMap);
        when(mockAiService.generateAuthorImageAsync(testArticleMap.get(ARTICLE_MAP_KEY_AUTHOR)))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));
        when(mockAiService.generateArticleImageAsync(testArticleMap.get(ARTICLE_MAP_KEY_HEADLINE)))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));
        when(mockImageStorageService.saveAuthorPhoto(any(), anyString()))
                .thenReturn(UUID.randomUUID().toString());
        when(mockImageStorageService.saveArticlePhoto(any(), anyString()))
                .thenReturn(new ArticlePhotoDto());

        Article article = articleService.createArticle();

        verify(mockAiService).generateTopic();
        verify(mockAiService).generateText(testTopic);
        verify(mockAiService).generateAuthorImageAsync(testArticleMap.get(ARTICLE_MAP_KEY_AUTHOR));
        verify(mockAiService)
                .generateArticleImageAsync(testArticleMap.get(ARTICLE_MAP_KEY_HEADLINE));
        assertNotNull(article);
        assertEquals(testArticleMap.get(ARTICLE_MAP_KEY_HEADLINE), article.getHeadline());
        assertEquals(testArticleMap.get(ARTICLE_MAP_KEY_AUTHOR), article.getAuthor());
        assertEquals(testArticleMap.get(ARTICLE_MAP_KEY_ARTICLE_BODY), article.getArticleBody());
        assertEquals(
                testArticleMap.get(ARTICLE_MAP_KEY_ARTICLE_PHOTO_CAPTION),
                article.getArticlePhoto().getCaption());
        assertEquals(
                testArticleMap.get(ARTICLE_MAP_KEY_ARTICLE_PHOTO_PHOTOGRAPHER),
                article.getArticlePhoto().getPhotographer());
    }

    @Test
    void givenCreateArticle_whenCalled_thenCallsImageStorageServiceMethods() {
        String authorPhotoFilename = UUID.randomUUID().toString();
        ArticlePhotoDto testArticlePhotoDto =
                new ArticlePhotoDto()
                        .setFullsize(UUID.randomUUID().toString())
                        .setThumbnail(UUID.randomUUID().toString());
        Map<String, String> testArticleMap = buildCompleteArticleMap(true);
        ImageResponse mockAuthorImageResponse = buildTestImageResponse();
        ImageResponse mockArticleImageResponse = buildTestImageResponse();

        when(mockAiService.generateTopic()).thenReturn(UUID.randomUUID().toString());
        when(mockAiService.generateText(any())).thenReturn(testArticleMap);
        when(mockAiService.generateAuthorImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockAuthorImageResponse));
        when(mockAiService.generateArticleImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockArticleImageResponse));
        when(mockImageStorageService.saveAuthorPhoto(
                        eq(mockAuthorImageResponse),
                        eq(testArticleMap.get(ARTICLE_MAP_KEY_AUTHOR))))
                .thenReturn(authorPhotoFilename);
        when(mockImageStorageService.saveArticlePhoto(
                        eq(mockArticleImageResponse),
                        eq(testArticleMap.get(ARTICLE_MAP_KEY_HEADLINE))))
                .thenReturn(testArticlePhotoDto);

        Article article = articleService.createArticle();

        verify(mockImageStorageService)
                .saveArticlePhoto(
                        mockArticleImageResponse, testArticleMap.get(ARTICLE_MAP_KEY_HEADLINE));
        verify(mockImageStorageService)
                .saveAuthorPhoto(
                        mockAuthorImageResponse, testArticleMap.get(ARTICLE_MAP_KEY_AUTHOR));
        assertNotNull(article);
        assertEquals(testArticlePhotoDto.getFullsize(), article.getArticlePhoto().getFullsize());
        assertEquals(testArticlePhotoDto.getThumbnail(), article.getArticlePhoto().getThumbnail());
        assertEquals(authorPhotoFilename, article.getAuthorPhoto());
    }

    @Test
    void givenCreateArticle_whenArticleIsFullyInitialized_thenSavesArticle() {
        Map<String, String> testArticleMap = buildCompleteArticleMap(true);
        ImageResponse mockImageResponse = buildTestImageResponse();

        when(mockAiService.generateTopic()).thenReturn(UUID.randomUUID().toString());
        when(mockAiService.generateText(any())).thenReturn(testArticleMap);
        when(mockAiService.generateAuthorImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));
        when(mockAiService.generateArticleImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));
        when(mockImageStorageService.saveAuthorPhoto(any(), anyString()))
                .thenReturn(UUID.randomUUID().toString());
        when(mockImageStorageService.saveArticlePhoto(any(), anyString()))
                .thenReturn(new ArticlePhotoDto());

        Article article = articleService.createArticle();

        verify(mockArticleRepository).save(article);
        assertNotNull(article);
        assertTrue(article.isFullyInitialized());
    }

    @Test
    void givenCreateArticle_whenArticleIsNotFullyInitialized_thenThrowsException() {
        Map<String, String> testArticleMap = buildCompleteArticleMap(false);

        when(mockAiService.generateTopic()).thenReturn(UUID.randomUUID().toString());
        when(mockAiService.generateText(any())).thenReturn(testArticleMap);
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
        Article mockArticle = new Article();

        when(mockArticleRepository.findById(uuid)).thenReturn(Optional.of(mockArticle));

        Article article = articleService.getArticleById(uuid);

        verify(mockArticleRepository).findById(uuid);
        assertNotNull(article);
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
        Page<Article> pagedArticles = buildPagedArticles();

        when(mockArticleRepository.findAll(pageable)).thenReturn(pagedArticles);

        Page<Article> articles = articleService.getPagedArticles(pageable);

        verify(mockArticleRepository).findAll(pageable);
        assertSame(articles, pagedArticles);
    }

    private Map<String, String> buildCompleteArticleMap(boolean isComplete) {
        Map<String, String> testArticleData = new HashMap<>();
        testArticleData.put(ARTICLE_MAP_KEY_HEADLINE, UUID.randomUUID().toString());
        testArticleData.put(
                ARTICLE_MAP_KEY_AUTHOR, isComplete ? UUID.randomUUID().toString() : null);
        testArticleData.put(ARTICLE_MAP_KEY_ARTICLE_BODY, UUID.randomUUID().toString());
        testArticleData.put(ARTICLE_MAP_KEY_ARTICLE_PHOTO_CAPTION, UUID.randomUUID().toString());
        testArticleData.put(
                ARTICLE_MAP_KEY_ARTICLE_PHOTO_PHOTOGRAPHER, UUID.randomUUID().toString());

        return testArticleData;
    }

    private ImageResponse buildTestImageResponse() {
        ImageGeneration generation = mock(ImageGeneration.class);

        return new ImageResponse(List.of(generation));
    }

    private Page<Article> buildPagedArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article().setAuthor(UUID.randomUUID().toString()));
        articles.add(new Article().setAuthor(UUID.randomUUID().toString()));

        Page<Article> articlePage = new PageImpl<>(articles);

        return articlePage;
    }
}
