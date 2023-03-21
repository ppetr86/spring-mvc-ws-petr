package com.shopapp.repository;

import com.shopapp.data.entity.product.ProductEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends IdBasedTimeRevisionRepository<ProductEntity> {
}
