package com.shopapp.repository;

import com.shopapp.data.entity.snapshots.ProductSnapshotEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSnapshotRepository extends IdBasedTimeSnapshotRepository<ProductSnapshotEntity> {
}
