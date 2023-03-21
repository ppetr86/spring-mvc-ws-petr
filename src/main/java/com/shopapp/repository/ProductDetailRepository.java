package com.shopapp.repository;

import com.shopapp.data.entity.product.ProductDetailEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends IdBasedTimeRevisionRepository<ProductDetailEntity> {
}
