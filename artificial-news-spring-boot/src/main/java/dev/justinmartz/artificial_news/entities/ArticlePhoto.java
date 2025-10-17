package dev.justinmartz.artificial_news.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ArticlePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID")
    private UUID id;

    private String filename;

    private String caption;

    private String photographer;

    public ArticlePhoto() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
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
