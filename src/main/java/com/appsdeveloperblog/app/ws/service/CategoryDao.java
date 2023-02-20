package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.CategoryEntity;

public interface CategoryDao extends IdTimeRevisionDao<CategoryEntity> {


    CategoryEntity findByName(String categoryName);


    boolean existsByName(String name);
}
