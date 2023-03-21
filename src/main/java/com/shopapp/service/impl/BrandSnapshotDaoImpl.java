package com.shopapp.service.impl;

import com.shopapp.data.entity.snapshots.BrandSnapshotEntity;
import com.shopapp.repository.BrandSnapshotRepository;
import com.shopapp.repository.IdBasedTimeSnapshotRepository;
import com.shopapp.service.BrandSnapshotDao;
import com.shopapp.service.impl.superclass.AbstractIdTimeSnapshotDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandSnapshotDaoImpl extends AbstractIdTimeSnapshotDaoImpl<BrandSnapshotEntity> implements BrandSnapshotDao {

    private final BrandSnapshotRepository brandSnapshotRepository;

    @Override
    public Class<BrandSnapshotEntity> getPojoClass() {
        return BrandSnapshotEntity.class;
    }

    @Override
    public IdBasedTimeSnapshotRepository<BrandSnapshotEntity> getRepository() {
        return this.brandSnapshotRepository;
    }
}
