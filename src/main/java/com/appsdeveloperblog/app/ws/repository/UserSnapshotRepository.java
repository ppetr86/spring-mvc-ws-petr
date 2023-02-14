package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.UserSnapshotEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSnapshotRepository extends IdBasedTimeSnapshotRepository<UserSnapshotEntity> {

}
