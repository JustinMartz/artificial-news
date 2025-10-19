package dev.justinmartz.artificial_news.services;

import org.springframework.ai.image.ImageResponse;

public interface ImageStorageService {
    String saveAuthorPhoto(ImageResponse imageResponse, String author);

    String saveArticlePhoto(ImageResponse imageResponse, String headline);
}
