package com.shopapp.service.impl;

import com.shopapp.data.entity.product.ProductImageEntity;
import com.shopapp.repository.IdBasedRepository;
import com.shopapp.repository.ProductImageRepository;
import com.shopapp.service.ProductImageDao;
import com.shopapp.service.impl.superclass.AbstractIdDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductImageDaoImpl extends AbstractIdDaoImpl<ProductImageEntity> implements ProductImageDao {

    private final ProductImageRepository productImageRepository;

    @Override
    public Class<ProductImageEntity> getPojoClass() {
        return ProductImageEntity.class;
    }

    @Override
    public IdBasedRepository<ProductImageEntity> getRepository() {
        return productImageRepository;
    }
}
