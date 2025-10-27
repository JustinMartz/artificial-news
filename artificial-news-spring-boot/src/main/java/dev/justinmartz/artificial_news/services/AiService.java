/* (C)2025 */
package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.models.ArticleDto;
import java.util.concurrent.CompletableFuture;
import org.springframework.ai.image.ImageResponse;

public interface AiService {
    CompletableFuture<ImageResponse> generateAuthorImageAsync(String author);

    CompletableFuture<ImageResponse> generateArticleImageAsync(String headline);

    String generateTopic();

    ArticleDto generateText(String topic);
}
