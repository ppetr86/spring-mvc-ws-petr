package com.shopapp.repository;

import com.shopapp.data.entity.snapshots.CategorySnapshotEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorySnapshotRepository extends IdBasedTimeSnapshotRepository<CategorySnapshotEntity> {
}
