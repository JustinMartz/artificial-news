package dev.justinmartz.artificial_news.exceptions;

import java.util.UUID;

public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(UUID id) {
        super("Could not find article with id: " + id);
    }
}
