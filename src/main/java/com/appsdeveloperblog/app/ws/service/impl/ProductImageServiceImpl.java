package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.ProductImageEntity;
import com.appsdeveloperblog.app.ws.repository.IdBasedRepository;
import com.appsdeveloperblog.app.ws.repository.ProductImageRepository;
import com.appsdeveloperblog.app.ws.service.ProductImageService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductImageServiceImpl extends AbstractIdBasedServiceImpl<ProductImageEntity> implements ProductImageService {

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
