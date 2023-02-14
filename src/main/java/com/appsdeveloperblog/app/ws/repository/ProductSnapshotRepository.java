package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.ProductSnapshotEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSnapshotRepository extends IdBasedTimeSnapshotRepository<ProductSnapshotEntity> {
}
