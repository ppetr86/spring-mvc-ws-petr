package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.CreditCardEntity;
import com.appsdeveloperblog.app.ws.data.entity.ProductDetailEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.CreditCardSnapshotEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.ProductDetailSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.CreditCardRepository;
import com.appsdeveloperblog.app.ws.service.CreditCardService;
import com.appsdeveloperblog.app.ws.service.CreditCardSnapshotService;
import com.appsdeveloperblog.app.ws.service.ProductDetailSnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedTimeRevisionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreditCardServiceImpl extends AbstractIdBasedTimeRevisionServiceImpl<CreditCardEntity> implements CreditCardService {

    private final CreditCardRepository cardRepository;

    private final CreditCardSnapshotService creditCardSnapshotService;

    @Override
    public void onBeforeWrite(CreditCardEntity dbObj) {
        super.onBeforeWrite(dbObj);

        final boolean dbObjExists = dbObj != null && dbObj.getId() != null && loadById(dbObj.getId()) != null;

        if (dbObjExists) {
            var snapshot = new CreditCardSnapshotEntity();
            snapshot.setMaxRevision(this.findMaxRevision());
            snapshot.setCreditCardNumber(dbObj.getCreditCardNumber());
            snapshot.setCvv(dbObj.getCvv());
            snapshot.setExpirationDate(dbObj.getExpirationDate());
            this.creditCardSnapshotService.save(snapshot);
        }
    }


    @Override
    public CreditCardRepository getRepository() {
        return this.cardRepository;
    }

    @Override
    public Class<CreditCardEntity> getPojoClass() {
        return CreditCardEntity.class;
    }
}
