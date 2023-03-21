package com.shopapp.service;

import com.shopapp.data.entity.snapshots.IdBasedTimeSnapshotEntity;
import com.shopapp.repository.IdBasedTimeSnapshotRepository;

public interface IdTimeSnapshotDao<T extends IdBasedTimeSnapshotEntity> extends IdTimeDao<T> {


    T getDataForRevision(Long revision);


    Class<T> getPojoClass();


    IdBasedTimeSnapshotRepository<T> getRepository();


    Long getSmallestRevisionGreaterThanThis(Long revision);

}
