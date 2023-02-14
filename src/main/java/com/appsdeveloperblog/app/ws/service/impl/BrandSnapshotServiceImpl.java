package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.BrandSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.BrandSnapshotRepository;
import com.appsdeveloperblog.app.ws.repository.IdBasedTimeSnapshotRepository;
import com.appsdeveloperblog.app.ws.service.BrandSnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedTimeSnapshotServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandSnapshotServiceImpl extends AbstractIdBasedTimeSnapshotServiceImpl<BrandSnapshotEntity> implements BrandSnapshotService {

    private final BrandSnapshotRepository brandSnapshotRepository;

    @Override
    public IdBasedTimeSnapshotRepository<BrandSnapshotEntity> getRepository() {
        return this.brandSnapshotRepository;
    }

    @Override
    public Class<BrandSnapshotEntity> getPojoClass() {
        return BrandSnapshotEntity.class;
    }
}