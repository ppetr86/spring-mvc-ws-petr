package com.shopapp.repository;

import com.shopapp.data.entity.snapshots.ProductDetailSnapshotEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailSnapshotRepository extends IdBasedTimeSnapshotRepository<ProductDetailSnapshotEntity> {
}
