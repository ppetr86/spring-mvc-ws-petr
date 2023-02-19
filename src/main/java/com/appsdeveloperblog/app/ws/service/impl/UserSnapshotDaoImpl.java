package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.UserSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.UserSnapshotRepository;
import com.appsdeveloperblog.app.ws.service.UserSnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdTimeSnapshotDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSnapshotDaoImpl extends AbstractIdTimeSnapshotDaoImpl<UserSnapshotEntity> implements UserSnapshotService {

    private final UserSnapshotRepository userSnapshotRepository;

    @Override
    public Class<UserSnapshotEntity> getPojoClass() {
        return UserSnapshotEntity.class;
    }

    @Override
    public UserSnapshotRepository getRepository() {
        return this.userSnapshotRepository;
    }
}
