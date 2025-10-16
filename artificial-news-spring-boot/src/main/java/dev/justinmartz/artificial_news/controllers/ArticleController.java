package dev.justinmartz.artificial_news.controllers;

import dev.justinmartz.artificial_news.entities.Article;
import dev.justinmartz.artificial_news.services.ArticleService;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<Article> createNewArticle() {
        return ResponseEntity.ok().body(articleService.createArticle());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable UUID id) {
        Article article = articleService.getArticleById(id);
        return ResponseEntity.ok().body(article);
    }

    @GetMapping
    public Page<Article> getAllArticles(Pageable pageable) {
        Page<Article> articles = articleService.getPagedArticles(pageable);

        return articles;
    }
}
