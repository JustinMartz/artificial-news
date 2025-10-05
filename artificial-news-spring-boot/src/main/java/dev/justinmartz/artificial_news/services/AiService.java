/* (C)2025 */
package dev.justinmartz.artificial_news.services;

import java.util.concurrent.CompletableFuture;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;

public interface AiService {
    ChatResponse generateTextResponse(Prompt prompt);

    CompletableFuture<ImageResponse> generateImageResponseAsync(ImagePrompt prompt);
}
