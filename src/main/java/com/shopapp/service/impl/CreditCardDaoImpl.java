package com.shopapp.service.impl;

import com.shopapp.data.entity.CreditCardEntity;
import com.shopapp.data.entity.snapshots.CreditCardSnapshotEntity;
import com.shopapp.repository.CreditCardRepository;
import com.shopapp.service.CreditCardDao;
import com.shopapp.service.CreditCardSnapshotDao;
import com.shopapp.service.impl.superclass.AbstractIdTimeRevisionDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreditCardDaoImpl extends AbstractIdTimeRevisionDaoImpl<CreditCardEntity> implements CreditCardDao {

    private final CreditCardRepository cardRepository;

    private final CreditCardSnapshotDao creditCardSnapshotDao;

    @Override
    public Class<CreditCardEntity> getPojoClass() {
        return CreditCardEntity.class;
    }

    @Override
    public CreditCardRepository getRepository() {
        return this.cardRepository;
    }

    @Override
    public void onBeforeWrite(CreditCardEntity dbObj) {
        super.onBeforeWrite(dbObj);
        final boolean dbObjExists = dbObj != null && dbObj.getId() != null && loadById(dbObj.getId()) != null;

        if (dbObjExists) {
            var dbStateOfObj = loadById(dbObj.getId());
            var snapshot = new CreditCardSnapshotEntity();
            snapshot.setMaxRevision(this.findMaxRevision());
            snapshot.setCreditCardNumber(dbStateOfObj.getCreditCardNumber());
            snapshot.setCvv(dbStateOfObj.getCvv());
            snapshot.setExpirationDate(dbStateOfObj.getExpirationDate());
            this.creditCardSnapshotDao.save(snapshot);
        }
    }
}
