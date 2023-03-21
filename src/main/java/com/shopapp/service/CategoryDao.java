package com.shopapp.service;

import com.shopapp.data.entity.CategoryEntity;

public interface CategoryDao extends IdTimeRevisionDao<CategoryEntity> {


    boolean existsByName(String name);


    CategoryEntity findByName(String categoryName);
}
