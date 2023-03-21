package com.shopapp.service.impl;

import com.shopapp.data.entity.snapshots.CreditCardSnapshotEntity;
import com.shopapp.repository.CreditCardSnapshotRepository;
import com.shopapp.repository.IdBasedTimeSnapshotRepository;
import com.shopapp.service.CreditCardSnapshotDao;
import com.shopapp.service.impl.superclass.AbstractIdTimeSnapshotDaoImpl;
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
