package dev.justinmartz.artificial_news.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.justinmartz.artificial_news.entities.Article;
import dev.justinmartz.artificial_news.models.ArticlePhotoDto;
import dev.justinmartz.artificial_news.repositories.ArticleRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {

    @Mock private ArticleRepository mockArticleRepository;

    @Mock private OpenAiServiceImpl mockOpenAiService;

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

    @Test
    void givenCreateArticle_whenCalled_thenCallsGenerateTopicAndGenerateText() {
        String testTopic = UUID.randomUUID().toString();
        Map<String, String> testArticleMap = buildTestArticleMap();
        ImageResponse mockImageResponse = buildTestImageResponse();
        when(mockOpenAiService.generateTopic()).thenReturn(testTopic);
        when(mockOpenAiService.generateText(testTopic)).thenReturn(testArticleMap);
        when(mockOpenAiService.generateAuthorImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));
        when(mockOpenAiService.generateArticleImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));
        when(mockImageStorageService.saveAuthorPhoto(any(), anyString()))
                .thenReturn(UUID.randomUUID().toString());
        when(mockImageStorageService.saveArticlePhoto(any(), anyString()))
                .thenReturn(new ArticlePhotoDto());

        Article article = articleService.createArticle();

        verify(mockOpenAiService).generateTopic();
        verify(mockOpenAiService).generateText(testTopic);
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

    private Map<String, String> buildTestArticleMap() {
        Map<String, String> testArticleData = new HashMap<>();
        testArticleData.put(ARTICLE_MAP_KEY_HEADLINE, UUID.randomUUID().toString());
        testArticleData.put(ARTICLE_MAP_KEY_AUTHOR, UUID.randomUUID().toString());
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
}
