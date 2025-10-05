package dev.justinmartz.artificial_news.services;

import java.util.concurrent.CompletableFuture;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.stereotype.Service;

@Service
public class OpenAiServiceImpl implements AiService {
    private final OpenAiChatModel chatModel;
    private final OpenAiImageModel imageModel;

    public OpenAiServiceImpl(OpenAiChatModel openAiChatModel, OpenAiImageModel openAiImageModel) {
        this.chatModel = openAiChatModel;
        this.imageModel = openAiImageModel;
    }

    @Override
    public ChatResponse generateTextResponse(Prompt prompt) throws RuntimeException {
        ChatResponse response = chatModel.call(prompt);
        return response;
    }

    @Override
    public CompletableFuture<ImageResponse> generateImageResponseAsync(ImagePrompt prompt) {
        return CompletableFuture.supplyAsync(() -> imageModel.call(prompt));
    }
}
