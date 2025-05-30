package dev.justinmartz.artificial_news.controllers;

import dev.justinmartz.artificial_news.entities.Article;
import dev.justinmartz.artificial_news.services.ArticleService;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
  private final ArticleService articleService;

  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @PostMapping
  public ResponseEntity<Article> getNewArticle() {
    return ResponseEntity.ok().body(articleService.createArticle());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Article> getArticleById(@PathVariable UUID id) {
    Article article = articleService.getArticleById(id);
    return ResponseEntity.ok().body(article);
  }
}
