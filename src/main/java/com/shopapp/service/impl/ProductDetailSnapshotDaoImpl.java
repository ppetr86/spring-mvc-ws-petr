package com.shopapp.service.impl;

import com.shopapp.data.entity.snapshots.ProductDetailSnapshotEntity;
import com.shopapp.repository.ProductDetailSnapshotRepository;
import com.shopapp.service.ProductDetailSnapshotDao;
import com.shopapp.service.impl.superclass.AbstractIdTimeSnapshotDaoImpl;
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
