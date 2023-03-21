package com.shopapp.service.impl;

import com.shopapp.data.entity.snapshots.UserSnapshotEntity;
import com.shopapp.repository.UserSnapshotRepository;
import com.shopapp.service.UserSnapshotDao;
import com.shopapp.service.impl.superclass.AbstractIdTimeSnapshotDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSnapshotDaoImpl extends AbstractIdTimeSnapshotDaoImpl<UserSnapshotEntity> implements UserSnapshotDao {

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
