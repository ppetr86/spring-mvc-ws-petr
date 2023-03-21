package com.shopapp.service.impl.superclass;

import com.shopapp.data.entity.snapshots.IdBasedTimeSnapshotEntity;
import com.shopapp.service.IdTimeSnapshotDao;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractIdTimeSnapshotDaoImpl<T extends IdBasedTimeSnapshotEntity> extends AbstractIdTimeDaoImpl<T> implements IdTimeSnapshotDao<T> {


    @Override
    public T getDataForRevision(final Long revision) {
        return getRepository().getDataForRevision(revision == null ? 0 : revision);
    }

    @Override
    public Long getSmallestRevisionGreaterThanThis(final Long revision) {
        return getRepository().getSmallestRevisionGreaterThanThis(revision == null ? 0 : revision);
    }
}
