package com.shopapp.data.definition;

import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entity.AuthenticationType;
import com.shopapp.data.entity.CreditCardEntity;

import java.util.Set;

public interface CustomerReadable {

    String getEmail();

    String getEncryptedPassword();

    String getEmailVerificationToken();

    boolean isVerified();

    AuthenticationType getAuthenticationType();

    AddressEntity getAddress();

    Set<CreditCardEntity> getCreditCards();
}
