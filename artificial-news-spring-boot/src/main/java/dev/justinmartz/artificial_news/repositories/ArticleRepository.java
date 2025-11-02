package dev.justinmartz.artificial_news.repositories;

import dev.justinmartz.artificial_news.entities.ArticleEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {}
