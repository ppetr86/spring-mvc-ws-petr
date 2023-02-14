package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.ProductDetailSnapshotEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailSnapshotRepository extends IdBasedTimeSnapshotRepository<ProductDetailSnapshotEntity> {
}
