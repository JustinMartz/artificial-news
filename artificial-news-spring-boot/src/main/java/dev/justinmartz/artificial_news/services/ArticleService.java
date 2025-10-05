package dev.justinmartz.artificial_news.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.justinmartz.artificial_news.entities.Article;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Article createArticle();

    String generateTopic();

    Map<String, String> generateText(String topic) throws JsonProcessingException;

    Article getArticleById(UUID id);

    Page<Article> getAllArticles(Pageable p);
}
