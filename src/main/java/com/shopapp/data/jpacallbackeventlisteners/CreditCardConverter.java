package com.shopapp.data.jpacallbackeventlisteners;

import com.shopapp.SpringApplicationContext;
import com.shopapp.service.EncryptionService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Created by jt on 6/30/22.
 */
@Converter
public class CreditCardConverter implements AttributeConverter<String, String> {
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return getEncryptionService().encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return getEncryptionService().decrypt(dbData);
    }

    private EncryptionService getEncryptionService() {

        return (EncryptionService) SpringApplicationContext.getBean("encryptionServiceImpl");
    }
}
