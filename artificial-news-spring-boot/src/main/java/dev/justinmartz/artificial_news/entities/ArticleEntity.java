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
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "Article")
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    @JsonIgnore
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    private String dateline;

    private String headline;

    private String author;

    @Column(name = "author_photo")
    private String authorPhoto;

    @Column(name = "article_body", columnDefinition = "TEXT")
    private String articleBody;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "article_photo_id")
    private ArticlePhotoEntity articlePhotoEntity;

    private String provider;

    private String model;

    @Column(name = "creation_time")
    private Long creationTime;

    public ArticleEntity() {}

    @JsonIgnore
    public boolean isFullyInitialized() {
        return this.createdAt != null
                && this.dateline != null
                && this.headline != null
                && this.author != null
                && this.authorPhoto != null
                && this.articleBody != null
                && this.articlePhotoEntity != null;
    }

    public UUID getId() {
        return id;
    }

    public ArticleEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public ArticleEntity setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getDateline() {
        return dateline;
    }

    public ArticleEntity setDateline(String dateline) {
        this.dateline = dateline;
        return this;
    }

    public String getHeadline() {
        return headline;
    }

    public ArticleEntity setHeadline(String headline) {
        this.headline = headline;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleEntity setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getAuthorPhoto() {
        return authorPhoto;
    }

    public ArticleEntity setAuthorPhoto(String authorPhoto) {
        this.authorPhoto = authorPhoto;
        return this;
    }

    public String getArticleBody() {
        return articleBody;
    }

    public ArticleEntity setArticleBody(String articleBody) {
        this.articleBody = articleBody;
        return this;
    }

    public ArticlePhotoEntity getArticlePhoto() {
        return articlePhotoEntity;
    }

    public ArticleEntity setArticlePhoto(ArticlePhotoEntity articlePhotoEntity) {
        this.articlePhotoEntity = articlePhotoEntity;
        return this;
    }

    public String getProvider() {
        return provider;
    }

    public ArticleEntity setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ArticleEntity setModel(String model) {
        this.model = model;
        return this;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public ArticleEntity setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArticleEntity articleEntity)) return false;
        return Objects.equals(id, articleEntity.id)
                && Objects.equals(createdAt, articleEntity.createdAt)
                && Objects.equals(dateline, articleEntity.dateline)
                && Objects.equals(headline, articleEntity.headline)
                && Objects.equals(author, articleEntity.author)
                && Objects.equals(authorPhoto, articleEntity.authorPhoto)
                && Objects.equals(articleBody, articleEntity.articleBody)
                && Objects.equals(articlePhotoEntity, articleEntity.articlePhotoEntity)
                && Objects.equals(provider, articleEntity.provider)
                && Objects.equals(model, articleEntity.model)
                && Objects.equals(creationTime, articleEntity.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                createdAt,
                dateline,
                headline,
                author,
                authorPhoto,
                articleBody,
                articlePhotoEntity,
                provider,
                model,
                creationTime);
    }
}
