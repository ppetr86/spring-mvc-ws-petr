package com.shopapp.data.jpacallbackeventlisteners;

import com.shopapp.SpringApplicationContext;
import com.shopapp.data.entity.CreditCardEntity;
import com.shopapp.service.EncryptionService;
import jakarta.persistence.*;


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
