package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.ProductImageEntity;
import com.appsdeveloperblog.app.ws.repository.IdBasedRepository;
import com.appsdeveloperblog.app.ws.repository.ProductImageRepository;
import com.appsdeveloperblog.app.ws.service.ProductImageDao;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdDaoImpl;
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
