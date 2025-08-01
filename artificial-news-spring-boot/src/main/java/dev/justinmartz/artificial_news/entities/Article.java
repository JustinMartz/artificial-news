package dev.justinmartz.artificial_news.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Article {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(columnDefinition = "UUID")
  private UUID id;

  @JsonIgnore
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  private String dateline;

  private String headline;

  private String author;

  @Column(name = "author_photo")
  private String authorPhoto;

  @Column(name = "article_body", columnDefinition = "TEXT")
  private String articleBody;

  @Column(name = "article_photo")
  private String articlePhoto;

  public Article() {}

  public boolean isFullyInitialized() {
    return this.createdAt != null
        && this.dateline != null
        && this.headline != null
        && this.author != null
        && this.authorPhoto != null
        && this.articleBody != null
        && this.articlePhoto != null;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getDateline() {
    return dateline;
  }

  public void setDateline(String dateline) {
    this.dateline = dateline;
  }

  public String getHeadline() {
    return headline;
  }

  public void setHeadline(String headline) {
    this.headline = headline;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getAuthorPhoto() {
    return authorPhoto;
  }

  public void setAuthorPhoto(String authorPhoto) {
    this.authorPhoto = authorPhoto;
  }

  public String getArticleBody() {
    return articleBody;
  }

  public void setArticleBody(String articleBody) {
    this.articleBody = articleBody;
  }

  public String getArticlePhoto() {
    return articlePhoto;
  }

  public void setArticlePhoto(String articlePhoto) {
    this.articlePhoto = articlePhoto;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Article article)) return false;
    return Objects.equals(id, article.id)
        && Objects.equals(createdAt, article.createdAt)
        && Objects.equals(dateline, article.dateline)
        && Objects.equals(headline, article.headline)
        && Objects.equals(author, article.author)
        && Objects.equals(authorPhoto, article.authorPhoto)
        && Objects.equals(articleBody, article.articleBody)
        && Objects.equals(articlePhoto, article.articlePhoto);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        id, createdAt, dateline, headline, author, authorPhoto, articleBody, articlePhoto);
  }

  @Override
  public String toString() {
    return "Article{"
        + "id="
        + id
        + ", createdAt="
        + createdAt
        + ", dateline='"
        + dateline
        + '\''
        + ", headline='"
        + headline
        + '\''
        + ", author='"
        + author
        + '\''
        + ", authorPhoto='"
        + authorPhoto
        + '\''
        + ", articleBody='"
        + articleBody
        + '\''
        + ", articlePhoto='"
        + articlePhoto
        + '\''
        + '}';
  }
}
