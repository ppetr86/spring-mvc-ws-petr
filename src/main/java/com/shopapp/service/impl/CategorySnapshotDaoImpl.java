package com.shopapp.service.impl;

import com.shopapp.data.entity.snapshots.CategorySnapshotEntity;
import com.shopapp.repository.CategorySnapshotRepository;
import com.shopapp.repository.IdBasedTimeSnapshotRepository;
import com.shopapp.service.CategorySnapshotDao;
import com.shopapp.service.impl.superclass.AbstractIdTimeSnapshotDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategorySnapshotDaoImpl extends AbstractIdTimeSnapshotDaoImpl<CategorySnapshotEntity> implements CategorySnapshotDao {

    private final CategorySnapshotRepository categorySnapshotRepository;

    @Override
    public Class<CategorySnapshotEntity> getPojoClass() {
        return CategorySnapshotEntity.class;
    }

    @Override
    public IdBasedTimeSnapshotRepository<CategorySnapshotEntity> getRepository() {
        return this.categorySnapshotRepository;
    }
}
