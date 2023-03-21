package com.shopapp.service.impl;

import com.shopapp.data.entity.snapshots.ProductSnapshotEntity;
import com.shopapp.repository.ProductSnapshotRepository;
import com.shopapp.service.ProductSnapshotDao;
import com.shopapp.service.impl.superclass.AbstractIdTimeSnapshotDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductSnapshotDaoImpl extends AbstractIdTimeSnapshotDaoImpl<ProductSnapshotEntity> implements ProductSnapshotDao {

    private final ProductSnapshotRepository productSnapshotRepository;

    @Override
    public Class<ProductSnapshotEntity> getPojoClass() {
        return ProductSnapshotEntity.class;
    }

    @Override
    public ProductSnapshotRepository getRepository() {
        return this.productSnapshotRepository;
    }
}
