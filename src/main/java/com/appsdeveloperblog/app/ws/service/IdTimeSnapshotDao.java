package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.IdBasedTimeSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.IdBasedTimeSnapshotRepository;

public interface IdTimeSnapshotDao<T extends IdBasedTimeSnapshotEntity> extends IdTimeDao<T> {


    T getDataForRevision(Long revision);


    Class<T> getPojoClass();


    IdBasedTimeSnapshotRepository<T> getRepository();


    Long getSmallestRevisionGreaterThanThis(Long revision);

}
