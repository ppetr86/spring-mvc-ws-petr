package com.appsdeveloperblog.app.ws.data.jpacallbackeventlisteners;

import com.appsdeveloperblog.app.ws.SpringApplicationContext;
import com.appsdeveloperblog.app.ws.data.entity.CreditCardEntity;
import com.appsdeveloperblog.app.ws.service.EncryptionService;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

/**
 * Created by jt on 6/30/22.
 */
public class CreditCardJPACallback {

    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate(CreditCardEntity creditCard) {
        System.out.println("before update was called...");
        creditCard.setCreditCardNumber(getEncryptionService().encrypt(creditCard.getCreditCardNumber()));
    }

    @PostPersist
    @PostLoad
    @PostUpdate
    public void postLoad(CreditCardEntity creditCard) {
        System.out.println("Post Load was called...");
        creditCard.setCreditCardNumber(getEncryptionService().decrypt(creditCard.getCreditCardNumber()));
    }

    private EncryptionService getEncryptionService() {
        return (EncryptionService) SpringApplicationContext.getBean("encryptionServiceImpl");
    }

}
