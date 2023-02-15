package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.CategoryEntity;

public interface CategoryService extends IdBasedTimeRevisionService<CategoryEntity> {


    CategoryEntity findByName(String parentCategoryName);


    boolean existsByName(String name);
}
