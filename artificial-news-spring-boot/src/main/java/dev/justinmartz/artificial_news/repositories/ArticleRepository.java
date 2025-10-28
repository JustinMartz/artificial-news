package dev.justinmartz.artificial_news.repositories;

import dev.justinmartz.artificial_news.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {
}
