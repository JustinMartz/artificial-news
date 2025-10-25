package dev.justinmartz.artificial_news.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import dev.justinmartz.artificial_news.entities.Article;
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
public class ArticleServiceTest {

    @Mock private ArticleRepository mockArticleRepository;

    @Mock private OpenAiServiceImpl mockOpenAiService;

    @InjectMocks private ArticleServiceImpl articleService;

    @BeforeEach
    void setup() {}

    @Test
    void givenCreateArticle_whenCalled_thenCallsGenerateTopicAndGenerateText() {
        String testTopic = buildTestTopic();
        Map<String, String> testArticleMap = buildTestArticleMap();
        ImageResponse mockImageResponse = buildTestImageResponse();
        when(mockOpenAiService.generateTopic()).thenReturn(testTopic);
        when(mockOpenAiService.generateText(testTopic));
        when(mockOpenAiService.generateAuthorImageAsync(any()))
                .thenReturn(CompletableFuture.completedFuture(mockImageResponse));

        Article article = articleService.createArticle();

        verify(mockOpenAiService).generateTopic();
        verify(mockOpenAiService).generateText(testTopic);
        //        assertEquals(testTopic, article.getHeadline());

    }

    private String buildTestTopic() {
        return UUID.randomUUID().toString();
    }

    private Map<String, String> buildTestArticleMap() {
        Map<String, String> testArticleData = new HashMap<>();
        testArticleData.put("headline", UUID.randomUUID().toString());
        testArticleData.put("author", UUID.randomUUID().toString());
        testArticleData.put("articleBody", UUID.randomUUID().toString());
        testArticleData.put("articlePhotoCaption", UUID.randomUUID().toString());
        testArticleData.put("articlePhotoPhotographer", UUID.randomUUID().toString());

        return testArticleData;
    }

    private ImageResponse buildTestImageResponse() {
        ImageGeneration generation = mock(ImageGeneration.class);
        when(generation.getOutput().getB64Json()).thenReturn("iVBORw0KGgoAAAANSUhEUgAAAAUA...");

        ImageResponse imageResponse = new ImageResponse(List.of(generation));

        return imageResponse;
    }
}
