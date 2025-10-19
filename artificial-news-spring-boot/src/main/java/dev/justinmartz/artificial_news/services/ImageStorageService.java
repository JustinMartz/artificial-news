package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.models.ArticlePhotoDto;
import org.springframework.ai.image.ImageResponse;

public interface ImageStorageService {
    String saveAuthorPhoto(ImageResponse imageResponse, String author);

    ArticlePhotoDto saveArticlePhoto(ImageResponse imageResponse, String headline);
}
