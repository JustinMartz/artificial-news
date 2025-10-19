/* (C)2025 */
package dev.justinmartz.artificial_news.services;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.springframework.ai.image.ImageResponse;

public interface AiService {
    CompletableFuture<ImageResponse> generateAuthorImageAsync(String author);

    CompletableFuture<ImageResponse> generateArticleImageAsync(String headline);

    String generateTopic();

    Map<String, String> generateText(String topic);
}
