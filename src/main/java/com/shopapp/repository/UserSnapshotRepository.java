package com.shopapp.repository;

import com.shopapp.data.entity.snapshots.UserSnapshotEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSnapshotRepository extends IdBasedTimeSnapshotRepository<UserSnapshotEntity> {

}
