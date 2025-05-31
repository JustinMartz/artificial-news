package dev.justinmartz.artificial_news.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.justinmartz.artificial_news.entities.Article;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ArticleService {
    Article createArticle();
    String generateTopic();
    Map<String, String> generateText(String topic) throws JsonProcessingException;
    Article getArticleById(UUID id);
    Page<Article> getAllArticles(Pageable p);
}
