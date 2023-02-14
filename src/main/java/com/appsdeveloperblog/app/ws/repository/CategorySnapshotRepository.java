package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.CategorySnapshotEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorySnapshotRepository extends IdBasedTimeSnapshotRepository<CategorySnapshotEntity> {
}
