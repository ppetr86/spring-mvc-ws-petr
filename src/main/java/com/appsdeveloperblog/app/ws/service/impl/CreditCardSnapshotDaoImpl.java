package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.CreditCardSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.CreditCardSnapshotRepository;
import com.appsdeveloperblog.app.ws.repository.IdBasedTimeSnapshotRepository;
import com.appsdeveloperblog.app.ws.service.CreditCardSnapshotDao;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdTimeSnapshotDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditCardSnapshotDaoImpl extends AbstractIdTimeSnapshotDaoImpl<CreditCardSnapshotEntity> implements CreditCardSnapshotDao {

    private final CreditCardSnapshotRepository creditCardSnapshotRepository;

    @Override
    public Class<CreditCardSnapshotEntity> getPojoClass() {
        return CreditCardSnapshotEntity.class;
    }

    @Override
    public IdBasedTimeSnapshotRepository<CreditCardSnapshotEntity> getRepository() {
        return this.creditCardSnapshotRepository;
    }
}
