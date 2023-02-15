package com.appsdeveloperblog.app.ws.service.impl.superclass;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.IdBasedTimeSnapshotEntity;
import com.appsdeveloperblog.app.ws.service.IdBasedTimeSnapshotService;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractIdBasedTimeSnapshotServiceImpl<T extends IdBasedTimeSnapshotEntity> extends AbstractIdBasedTimeServiceImpl<T> implements IdBasedTimeSnapshotService<T> {


    @Override
    public T getDataForRevision(Long revision) {
        return getRepository().getDataForRevision(revision == null ? 0 : revision);
    }

    @Override
    public Long getSmallestRevisionGreaterThanThis(Long revision) {
        return getRepository().getSmallestRevisionGreaterThanThis(revision == null ? 0 : revision);
    }
}
