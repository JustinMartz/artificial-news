package dev.justinmartz.artificial_news.services;

import dev.justinmartz.artificial_news.repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @Mock private ArticleRepository mockArticleRepository;

    @Mock private OpenAiServiceImpl mockOpenAiService;

    @InjectMocks private ArticleServiceImpl articleService;

    @BeforeEach
    void setup() {}

    @Test
    void createArticle_createsNewArticleSuccessfully() {}
}
