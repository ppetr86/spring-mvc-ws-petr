package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.BrandSnapshotEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandSnapshotRepository extends IdBasedTimeSnapshotRepository<BrandSnapshotEntity> {
}
