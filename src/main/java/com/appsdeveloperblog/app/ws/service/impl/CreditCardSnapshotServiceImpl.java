package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.snapshots.CreditCardSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.CreditCardSnapshotRepository;
import com.appsdeveloperblog.app.ws.repository.IdBasedTimeSnapshotRepository;
import com.appsdeveloperblog.app.ws.service.CreditCardSnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedTimeSnapshotServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditCardSnapshotServiceImpl extends AbstractIdBasedTimeSnapshotServiceImpl<CreditCardSnapshotEntity> implements CreditCardSnapshotService {

    private final CreditCardSnapshotRepository creditCardSnapshotRepository;

    @Override
    public IdBasedTimeSnapshotRepository<CreditCardSnapshotEntity> getRepository() {
        return this.creditCardSnapshotRepository;
    }

    @Override
    public Class<CreditCardSnapshotEntity> getPojoClass() {
        return CreditCardSnapshotEntity.class;
    }
}
