package dev.justinmartz.artificial_news.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "article")
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "article_photo_id")
    private ArticlePhoto articlePhoto;

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

    public Article setId(UUID id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Article setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getDateline() {
        return dateline;
    }

    public Article setDateline(String dateline) {
        this.dateline = dateline;
        return this;
    }

    public String getHeadline() {
        return headline;
    }

    public Article setHeadline(String headline) {
        this.headline = headline;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Article setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getAuthorPhoto() {
        return authorPhoto;
    }

    public Article setAuthorPhoto(String authorPhoto) {
        this.authorPhoto = authorPhoto;
        return this;
    }

    public String getArticleBody() {
        return articleBody;
    }

    public Article setArticleBody(String articleBody) {
        this.articleBody = articleBody;
        return this;
    }

    public ArticlePhoto getArticlePhoto() {
        return articlePhoto;
    }

    public Article setArticlePhoto(ArticlePhoto articlePhoto) {
        this.articlePhoto = articlePhoto;
        return this;
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
