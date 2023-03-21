package com.shopapp.repository;

import com.shopapp.data.entity.snapshots.BrandSnapshotEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandSnapshotRepository extends IdBasedTimeSnapshotRepository<BrandSnapshotEntity> {
}
