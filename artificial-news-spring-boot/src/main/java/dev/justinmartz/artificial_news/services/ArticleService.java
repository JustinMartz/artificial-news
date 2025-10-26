package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.entities.Article;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Article createArticle();

    Article getArticleById(UUID id);

    Page<Article> getPagedArticles(Pageable p);
}
