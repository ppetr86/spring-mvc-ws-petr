package com.shopapp.repository;

import com.shopapp.data.entity.CategoryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends IdBasedTimeRevisionRepository<CategoryEntity> {
}
