package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.CategorySnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.CategorySnapshotRepository;
import com.appsdeveloperblog.app.ws.repository.IdBasedTimeSnapshotRepository;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedTimeSnapshotServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategorySnapshotServiceImpl extends AbstractIdBasedTimeSnapshotServiceImpl<CategorySnapshotEntity> implements CategorySnapshotService {

    private final CategorySnapshotRepository categorySnapshotRepository;

    @Override
    public IdBasedTimeSnapshotRepository<CategorySnapshotEntity> getRepository() {
        return this.categorySnapshotRepository;
    }

    @Override
    public Class<CategorySnapshotEntity> getPojoClass() {
        return CategorySnapshotEntity.class;
    }
}
