package dev.justinmartz.artificial_news.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired private ArticleRepository articleRepository;

    @Test
    @Sql("classpath:/seed-data.sql")
    void givenSave_whenValidArticle_thenPersistsInDatabase() {}
}
