package dev.justinmartz.artificial_news.models;

public class ArticleDto {
    private String provider;

    private String model;

    private String headline;

    private String author;

    private String articleBody;

    private String articlePhotoCaption;

    private String articlePhotoPhotographer;

    private String authorPhotoFilename;

    private Long creationTime;

    public String getProvider() {
        return provider;
    }

    public ArticleDto setProvider(String provider) {
        this.provider = provider;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ArticleDto setModel(String model) {
        this.model = model;
        return this;
    }

    public String getHeadline() {
        return headline;
    }

    public ArticleDto setHeadline(String headline) {
        this.headline = headline;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getArticleBody() {
        return articleBody;
    }

    public ArticleDto setArticleBody(String articleBody) {
        this.articleBody = articleBody;
        return this;
    }

    public String getArticlePhotoCaption() {
        return articlePhotoCaption;
    }

    public ArticleDto setArticlePhotoCaption(String articlePhotoCaption) {
        this.articlePhotoCaption = articlePhotoCaption;
        return this;
    }

    public String getArticlePhotoPhotographer() {
        return articlePhotoPhotographer;
    }

    public ArticleDto setArticlePhotoPhotographer(String articlePhotoPhotographer) {
        this.articlePhotoPhotographer = articlePhotoPhotographer;
        return this;
    }

    public String getAuthorPhotoFilename() {
        return authorPhotoFilename;
    }

    public ArticleDto setAuthorPhotoFilename(String authorPhotoFilename) {
        this.authorPhotoFilename = authorPhotoFilename;
        return this;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public ArticleDto setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
        return this;
    }
}
