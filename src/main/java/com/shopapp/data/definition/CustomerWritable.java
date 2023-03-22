package com.shopapp.data.definition;

import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entity.AuthenticationType;
import com.shopapp.data.entity.CreditCardEntity;

import java.util.Set;

public interface CustomerWritable {

    void setEmail(String email);

    void setEncryptedPassword(String encryptedPassword);

    void setEmailVerificationToken(String emailVerificationToken);

    void setVerified(boolean verified);

    void setAuthenticationType(AuthenticationType authenticationType);

    void setAddress(AddressEntity address);

    void setCreditCards(Set<CreditCardEntity> creditCards);


}
