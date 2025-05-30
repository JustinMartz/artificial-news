package dev.justinmartz.artificial_news.exceptions;

public class ArticleNotCreatedException extends RuntimeException {
    public ArticleNotCreatedException(String s, Exception e) {
        super("Error creating article in " + s + "\n" + e.getMessage());
    }

}
