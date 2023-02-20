package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.ProductDetailSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.ProductDetailSnapshotRepository;
import com.appsdeveloperblog.app.ws.service.ProductDetailSnapshotDao;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdTimeSnapshotDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductDetailSnapshotDaoImpl extends AbstractIdTimeSnapshotDaoImpl<ProductDetailSnapshotEntity> implements ProductDetailSnapshotDao {

    private final ProductDetailSnapshotRepository productDetailSnapshotRepository;

    @Override
    public Class<ProductDetailSnapshotEntity> getPojoClass() {
        return ProductDetailSnapshotEntity.class;
    }

    @Override
    public ProductDetailSnapshotRepository getRepository() {
        return this.productDetailSnapshotRepository;
    }
}
