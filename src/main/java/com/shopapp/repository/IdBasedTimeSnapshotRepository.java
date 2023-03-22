package com.shopapp.repository;

import com.shopapp.data.entity.snapshots.IdBasedTimeSnapshotEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IdBasedTimeSnapshotRepository<T extends IdBasedTimeSnapshotEntity> extends IdBasedTimeRepository<T> {

    @Query("SELECT u from #{#entityName} u WHERE u.maxRevision = :revision")
    T getDataForRevision(long revision);

    @Query("SELECT MIN(maxRevision) from #{#entityName} WHERE maxRevision >= :revision")
    Long getSmallestRevisionGreaterThanThis(long revision);
}
