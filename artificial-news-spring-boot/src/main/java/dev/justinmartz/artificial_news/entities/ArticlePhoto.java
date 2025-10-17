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

    private String filename;

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

    public String getFilename() {
        return filename;
    }

    public ArticlePhoto setFilename(String filename) {
        this.filename = filename;
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
                && Objects.equals(filename, that.filename)
                && Objects.equals(caption, that.caption)
                && Objects.equals(photographer, that.photographer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, filename, caption, photographer);
    }
}
