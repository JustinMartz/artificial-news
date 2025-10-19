package dev.justinmartz.artificial_news.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.justinmartz.artificial_news.exceptions.ArticleNotCreatedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class OpenAiServiceImpl implements AiService {
    private final OpenAiChatModel chatModel;
    private final OpenAiImageModel imageModel;
    private static final Executor imageExecutor = Executors.newFixedThreadPool(10);

    public OpenAiServiceImpl(OpenAiChatModel openAiChatModel, OpenAiImageModel openAiImageModel) {
        this.chatModel = openAiChatModel;
        this.imageModel = openAiImageModel;
    }

    @Override
    public CompletableFuture<ImageResponse> generateAuthorImageAsync(String author) {
        String prompt =
                "Generate a black and white photograph of a journalist using the name "
                        + author
                        + """
                         as a basis for the subject's ethnic identity and gender. The background is white and the subject's face
                        is dramatic but professional. Subject is middle-aged and looking straight at the camera with a neutral expression.
                        The composition of the photograph is the subject's head and neck completely in frame.
                        There is space between the top of the subject's head an the top of the image.
                        Subject is centered in the image. Subject's face is well-lit by studio lighting.
                        The entire head is in frame of the shot.
                        Photorealistic and high detail.
                        Shot by arriflex 35 BL Camera Canon K35 Prime Lenses
                        """;

        ImagePrompt imagePrompt =
                new ImagePrompt(
                        prompt,
                        OpenAiImageOptions.builder()
                                .model("dall-e-2")
                                .N(1)
                                .responseFormat("b64_json")
                                .height(256)
                                .width(256)
                                .build());

        try {
            return CompletableFuture.supplyAsync(
                    () -> {
                        try {
                            return imageModel.call(imagePrompt);
                        } catch (RestClientException e) {
                            throw new CompletionException(e);
                        }
                    },
                    imageExecutor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CompletableFuture<ImageResponse> generateArticleImageAsync(String headline) {
        String prompt =
                "Generate a color photograph to accompany a news article."
                        + "The headline of the article is "
                        + headline
                        + ". "
                        + """
                         The composition of the photograph should encompass all elements of the headline.
                         The photograph should be a natural, photorealistic scene depicting the event in the headline.
                         The photograph should be photorealistic, dramatic, and in the style of photojournalism.
                         Subjects in the photograph are in focus and not blurry.
                        """;

        ImagePrompt imagePrompt =
                new ImagePrompt(
                        prompt,
                        OpenAiImageOptions.builder()
                                .model("dall-e-3")
                                .quality("standard")
                                .style("natural")
                                .N(1)
                                .responseFormat("b64_json")
                                .build());

        try {
            return CompletableFuture.supplyAsync(
                    () -> {
                        try {
                            return imageModel.call(imagePrompt);
                        } catch (RestClientException e) {
                            throw new CompletionException(e);
                        }
                    },
                    imageExecutor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateTopic() {
        String userText = "Generate one topic of interest to write a news article about.";
        Message userMessage = new UserMessage(userText);
        String systemText =
                """
                You are a helpful AI assistant that generates one interesting news article topic.
                The topic should be a one, two, or three-word phrase that succinctly defines a newsworthy event somewhere in America.
                The topic should be specific and should include specific adjectives and city names.
                The topic can be weird and have no prior context in real life.
                Examples of topics are "midnight taco strike", "mayor bans spoons", and "raccoon parade in Omaha".
                """;
        Message systemMessage = new SystemMessage(systemText);
        Prompt prompt =
                new Prompt(
                        List.of(systemMessage, userMessage),
                        OpenAiChatOptions.builder()
                                .model(OpenAiApi.ChatModel.GPT_4_O)
                                .temperature(0.9)
                                .build());

        ChatResponse response = chatModel.call(prompt);

        return response.getResult().getOutput().toString();
    }

    @Override
    public Map<String, String> generateText(String topic) {
        Map<String, String> articleData = new HashMap<>();
        String userText =
                """
                Give me a news article three paragraphs in length about {topic}.
                The article length must be three paragraphs. Article text can include quotes from named people and
                  answer the "what", "where", "when", "who", and "why" of {topic}.
                The article must be three paragraphs in length.
                """;
        Message userMessage = new UserMessage(userText);
        String systemText =
                """
                You are a helpful AI assistant that is required to fulfill the conditions of the prompt.
                You should generate a headline, a name for the writer, and three paragraphs of an article about {topic}.
                You should also generate a one-sentence photograph caption based on the article text and topic.
                You should also generate a first and last name of a photographer.
                Possibilities of writer's name include any gender and any ethnic background.
                The headline should be catchy but professional.
                The writing should be entertaining and informative in the style of the New York Times.
                Write your output for the writer's name in JSON with a key called "author" for the writer.
                Write your output for the headline in JSON with a key called "headline".
                Write your output for all three article paragraphs in the same key called "articleBody". There should only be one "articleBody" key in the JSON output.
                Write your output for the photograph caption in a key called "articlePhotoCaption".
                Write your output for the photographer name in a key called "articlePhotoPhotographer".
                Paragraphs should be separated by an escaped newline character but all contained within the same string.
                The response is not intended for markdown and should not be escaped with backticks for JSON markdown.
                """;
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemText);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("topic", topic));
        String jsonSchema =
                """
                {
                    "type": "object",
                    "properties": {
                        "headline": {
                            "type": "string"
                        },
                        "author": {
                            "type": "string"
                        },
                        "articleBody": {
                            "type": "string"
                        },
                        "articlePhotoCaption": {
                            "type": "string"
                        },
                        "articlePhotoPhotographer": {
                            "type": "string"
                        }
                    },
                "required": ["headline", "author", "articleBody", "articlePhotoCaption", "articlePhotoPhotographer"],
                "additionalProperties": false
                }
                """;
        Prompt prompt =
                new Prompt(
                        List.of(systemMessage, userMessage),
                        OpenAiChatOptions.builder()
                                .model(OpenAiApi.ChatModel.GPT_4_O)
                                .temperature(0.9)
                                .responseFormat(
                                        new ResponseFormat(
                                                ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                                .build());

        ChatResponse response = chatModel.call(prompt);

        String articleJson = response.getResult().getOutput().getText();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(articleJson);
        } catch (JsonProcessingException e) {
            throw new ArticleNotCreatedException("generateText(): ", e);
        }

        articleData.put("headline", jsonNode.get("headline").asText());
        articleData.put("author", jsonNode.get("author").asText());
        articleData.put("articleBody", jsonNode.get("articleBody").asText());
        articleData.put("articlePhotoCaption", jsonNode.get("articlePhotoCaption").asText());
        articleData.put(
                "articlePhotoPhotographer", jsonNode.get("articlePhotoPhotographer").asText());

        return articleData;
    }
}
