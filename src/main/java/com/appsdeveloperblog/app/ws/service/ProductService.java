package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.ProductEntity;

public interface ProductService extends IdBasedTimeRevisionService<ProductEntity> {

    boolean existsByName(String name);


    ProductEntity findByName(String name);
}
