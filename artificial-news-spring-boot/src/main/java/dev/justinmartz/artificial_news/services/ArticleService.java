package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.entities.ArticleEntity;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    ArticleEntity createArticle();

    ArticleEntity getArticleById(UUID id);

    Page<ArticleEntity> getPagedArticles(Pageable p);
}
