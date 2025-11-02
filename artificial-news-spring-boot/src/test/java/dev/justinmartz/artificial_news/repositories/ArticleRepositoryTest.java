package dev.justinmartz.artificial_news.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.justinmartz.artificial_news.entities.ArticleEntity;
import dev.justinmartz.artificial_news.entities.ArticlePhotoEntity;
import dev.justinmartz.artificial_news.exceptions.ArticleNotFoundException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired private ArticleRepository articleRepository;

    private static final UUID ARTICLE_1_ID =
            UUID.fromString("e4885aa2-7e74-42ed-9160-008ccbef9cc9");
    private static final String ARTICLE_1_HEADLINE =
            "Diving into the Rhythm: Miami's Unique Underwater Jazz Festival";
    private static final String SORT_BY_CREATED_AT = "createdAt";
    private static final String EXCEPTION_MESSAGE = "Could not find article with id: ";

    @Test
    @Sql("classpath:/seed-data.sql")
    void given_save_when_passedValidArticleEntity_then_persistsInDatabase() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity
                .setArticleBody(UUID.randomUUID().toString())
                .setArticlePhoto(new ArticlePhotoEntity())
                .setAuthor(UUID.randomUUID().toString())
                .setAuthorPhoto(UUID.randomUUID().toString())
                .setCreatedAt(OffsetDateTime.now())
                .setCreationTime(Long.parseLong("12345"))
                .setDateline(UUID.randomUUID().toString())
                .setHeadline(UUID.randomUUID().toString())
                .setModel(UUID.randomUUID().toString())
                .setProvider(UUID.randomUUID().toString());

        ArticleEntity savedArticleEntity = articleRepository.save(articleEntity);

        assertNotNull(savedArticleEntity);
        assertNotNull(savedArticleEntity.getId());
        assertNotNull(savedArticleEntity.getArticlePhoto().getId());
        assertEquals(articleEntity.getCreationTime(), savedArticleEntity.getCreationTime());
    }

    @Test
    @Sql("classpath:/seed-data.sql")
    void given_findById_when_passedValidId_then_ReturnsArticle() {
        Optional<ArticleEntity> articleEntityOptional = articleRepository.findById(ARTICLE_1_ID);
        ArticleEntity articleEntity = articleEntityOptional.get();

        assertNotNull(articleEntity);
        assertEquals(ARTICLE_1_ID, articleEntity.getId());
        assertEquals(ARTICLE_1_HEADLINE, articleEntity.getHeadline());
    }

    @Test
    @Sql("classpath:/seed-data.sql")
    void given_findById_when_passedInvalidId_then_ThrowsException() {
        UUID id = UUID.randomUUID();
        ArticleNotFoundException thrown =
                assertThrows(
                        ArticleNotFoundException.class,
                        () ->
                                articleRepository
                                        .findById(id)
                                        .orElseThrow(() -> new ArticleNotFoundException(id)));

        assertEquals(EXCEPTION_MESSAGE + id, thrown.getMessage());
    }

    @Test
    @Sql("classpath:/seed-data.sql")
    void given_findAll_when_passedValidPageable_then_returnsAllArticles() {
        Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, SORT_BY_CREATED_AT);

        Page<ArticleEntity> articleEntityPage = articleRepository.findAll(pageable);

        assertEquals(3, articleEntityPage.getTotalElements());
        assertTrue(
                articleEntityPage
                        .getContent()
                        .get(0)
                        .getCreatedAt()
                        .isBefore(articleEntityPage.getContent().get(1).getCreatedAt()));
    }
}
