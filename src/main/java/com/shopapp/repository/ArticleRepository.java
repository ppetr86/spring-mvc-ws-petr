package com.shopapp.repository;

import com.shopapp.data.entity.article.ArticleEntity;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends IdBasedRepository<ArticleEntity> {

    @Query("SELECT a FROM ArticleEntity a WHERE a.alias = ?1")
    ArticleEntity findByAlias(String alias);
}
