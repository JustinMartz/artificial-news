package dev.justinmartz.artificial_news.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.justinmartz.artificial_news.entities.Article;
import dev.justinmartz.artificial_news.entities.ArticlePhoto;
import dev.justinmartz.artificial_news.exceptions.ArticleNotCreatedException;
import dev.justinmartz.artificial_news.exceptions.ArticleNotFoundException;
import dev.justinmartz.artificial_news.repositories.ArticleRepository;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Value("${image.upload.dir}")
    private String uploadDirectory;

    private final ArticleRepository articleRepository;
    private final AiService aiService;
    private static final Executor imageExecutor = Executors.newFixedThreadPool(10);

    public ArticleServiceImpl(ArticleRepository articleRepository, AiService aiService) {
        this.articleRepository = articleRepository;
        this.aiService = aiService;
    }

    @Override
    public Article createArticle() {
        Article article = new Article();
        ArticlePhoto articlePhoto = new ArticlePhoto();

        try {
            String topic = generateTopic();
            Map<String, String> articleMap = generateText(topic);

            article.setHeadline(articleMap.get("headline"));
            article.setAuthor(articleMap.get("author"));
            article.setArticleBody(articleMap.get("articleBody"));
            article.setArticlePhoto(
                    articlePhoto
                            .setCaption(articleMap.get("articlePhotoCaption"))
                            .setPhotographer(articleMap.get("articlePhotoPhotographer")));
        } catch (JsonProcessingException | RuntimeException e) {
            throw new ArticleNotCreatedException("createArticle(), line 70", e);
        }

        try {
            CompletableFuture<String> authorPhotoFuture =
                    CompletableFuture.supplyAsync(
                            () -> {
                                try {
                                    return generateAuthorPhoto(article.getAuthor());
                                } catch (CompletionException e) {
                                    throw new CompletionException(e);
                                }
                            },
                            imageExecutor);

            CompletableFuture<String> articlePhotoFuture =
                    CompletableFuture.supplyAsync(
                            () -> {
                                try {
                                    return generateArticlePhoto(article.getHeadline());
                                } catch (RestClientException | IOException e) {
                                    throw new CompletionException(e);
                                }
                            },
                            imageExecutor);

            CompletableFuture.allOf(authorPhotoFuture, articlePhotoFuture).join();

            article.setAuthorPhoto(authorPhotoFuture.join());
            article.setArticlePhoto(articlePhoto.setFilename(articlePhotoFuture.join()));

        } catch (CompletionException e) {
            throw new ArticleNotCreatedException("createArticle() - image generation failed: ", e);
        }

        article.setCreatedAt(LocalDateTime.now());
        article.setDateline(formatUTCtoDateline(LocalDateTime.now()));

        if (article.isFullyInitialized()) {
            articleRepository.save(article);
            return article;
        } else {
            throw new ArticleNotCreatedException(
                    "createArticle(), article incomplete", new RuntimeException());
        }
    }

    @Override
    public String generateTopic() throws RuntimeException {
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

        ChatResponse response = aiService.generateTextResponse(prompt);

        return response.getResult().getOutput().toString();
    }

    @Override
    public Map<String, String> generateText(String topic)
            throws RuntimeException, JsonProcessingException {
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

        ChatResponse response = aiService.generateTextResponse(prompt);

        String articleJson = response.getResult().getOutput().getText();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(articleJson);
        articleData.put("headline", jsonNode.get("headline").asText());
        articleData.put("author", jsonNode.get("author").asText());
        articleData.put("articleBody", jsonNode.get("articleBody").asText());

        return articleData;
    }

    @Override
    public Article getArticleById(UUID id) {
        return articleRepository.findById(id).orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @Override
    public Page<Article> getPagedArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);

        return articles;
    }

    private String generateAuthorPhoto(String author) {
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
                                .height(256)
                                .width(256)
                                .build());

        try {
            ImageResponse imageResponse = aiService.generateImageResponseAsync(imagePrompt).get();
            String url = imageResponse.getResult().getOutput().getUrl();
            return fetchAndSaveAuthorPhoto(url, author);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String fetchAndSaveAuthorPhoto(String url, String author) throws IOException {
        // Produces something like Avery-Williams-1721680383905.png
        String filename =
                author.replaceAll("\\s+", "-") + "-" + System.currentTimeMillis() + ".png";

        URL imageUrl = new URL(url);
        BufferedImage image = ImageIO.read(imageUrl);
        ImageIO.write(image, "png", new File(uploadDirectory, filename));

        return filename;
    }

    private String formatUTCtoDateline(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL d, yyyy • h:mm a");
        return localDateTime.format(formatter);
    }

    private String generateArticlePhoto(String headline) throws RestClientException, IOException {
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
            ImageResponse imageResponse = aiService.generateImageResponseAsync(imagePrompt).get();
            String url = imageResponse.getResult().getOutput().getUrl();
            String base64Image = imageResponse.getResult().getOutput().getB64Json();
            return fetchAndSaveArticlePhoto(base64Image, headline);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String fetchAndSaveArticlePhoto(String base64Image, String headline)
            throws IOException {
        //     Produces something like
        //     Undead-Fun-Zombie-Apocalypse-Marathon-Takes-Over-New-York-City-1721686055684.png
        String filename =
                headline.replaceAll("[\\s,:]+", "-") + "-" + System.currentTimeMillis() + ".png";

        byte[] imageBytes = Base64.getDecoder().decode(base64Image);

        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            BufferedImage image = ImageIO.read(bis);
            if (image == null) {
                throw new ArticleNotCreatedException(
                        "fetchAndSaveArticlePhoto()", new IOException());
            }

            File outputFile = new File(uploadDirectory, filename);
            ImageIO.write(image, "png", outputFile);

            return filename;
        }
    }
}
