package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.ProductSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.ProductSnapshotRepository;
import com.appsdeveloperblog.app.ws.service.ProductSnapshotDao;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdTimeSnapshotDaoImpl;
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
