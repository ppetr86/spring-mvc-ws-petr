package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.CategoryEntity;

public interface CategoryService extends IdTimeRevisionDao<CategoryEntity> {


    CategoryEntity findByName(String categoryName);


    boolean existsByName(String name);
}
