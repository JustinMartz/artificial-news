package dev.justinmartz.artificial_news.repositories;

import dev.justinmartz.artificial_news.entities.ArticlePhotoEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlePhotoRepository extends JpaRepository<ArticlePhotoEntity, UUID> {}
