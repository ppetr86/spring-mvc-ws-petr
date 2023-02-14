package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.ProductDetailEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends IdBasedTimeRevisionRepository<ProductDetailEntity> {
}
