package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.IdBasedTimeSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.IdBasedTimeSnapshotRepository;

public interface IdBasedTimeSnapshotService<T extends IdBasedTimeSnapshotEntity> extends IdBasedTimeService<T> {


    IdBasedTimeSnapshotRepository<T> getRepository();

    Class<T> getPojoClass();

    Long getSmallestRevisionGreaterThanThis(Long revision);

    T getDataForRevision(Long revision);

}
