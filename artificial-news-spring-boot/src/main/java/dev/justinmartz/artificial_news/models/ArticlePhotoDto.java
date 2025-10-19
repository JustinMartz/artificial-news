package dev.justinmartz.artificial_news.models;

import java.util.Objects;

public class ArticlePhotoDto {
    private String fullsize;

    private String thumbnail;

    public ArticlePhotoDto() {}

    public String getFullsize() {
        return fullsize;
    }

    public ArticlePhotoDto setFullsize(String fullsize) {
        this.fullsize = fullsize;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public ArticlePhotoDto setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ArticlePhotoDto that)) return false;
        return Objects.equals(fullsize, that.fullsize) && Objects.equals(thumbnail, that.thumbnail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullsize, thumbnail);
    }
}
