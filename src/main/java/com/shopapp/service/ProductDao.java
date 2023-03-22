package com.shopapp.service;

import com.shopapp.data.entity.product.ProductEntity;

public interface ProductDao extends IdTimeRevisionDao<ProductEntity> {

    boolean existsByName(String name);

    ProductEntity findByName(String name);
}
