package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.CategoryEntity;

public interface CategoryDao extends IdTimeRevisionDao<CategoryEntity> {


    boolean existsByName(String name);


    CategoryEntity findByName(String categoryName);
}
