package dev.justinmartz.artificial_news.controllers;

import dev.justinmartz.artificial_news.entities.Article;
import dev.justinmartz.artificial_news.services.ArticleService;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

  @GetMapping
  public ResponseEntity<List<Article>> getAllArticles() {
    List<Article> articles = articleService.getAllArticles();

    if (articles == null || articles.size() == 0) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok().body(articles);
    }
  }
}
