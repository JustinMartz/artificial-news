package dev.justinmartz.artificial_news.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "article_photo")
public class ArticlePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    @JsonIgnore
    private UUID id;

    private String fullsize;

    private String thumbnail;

    private String caption;

    private String photographer;

    public ArticlePhoto() {}

    public UUID getId() {
        return id;
    }

    public ArticlePhoto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getFullsize() {
        return fullsize;
    }

    public ArticlePhoto setFullsize(String fullsize) {
        this.fullsize = fullsize;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ArticlePhoto setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public ArticlePhoto setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public String getPhotographer() {
        return photographer;
    }

    public ArticlePhoto setPhotographer(String photographer) {
        this.photographer = photographer;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArticlePhoto that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(fullsize, that.fullsize)
                && Objects.equals(thumbnail, that.thumbnail)
                && Objects.equals(caption, that.caption)
                && Objects.equals(photographer, that.photographer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullsize, thumbnail, caption, photographer);
    }
}
