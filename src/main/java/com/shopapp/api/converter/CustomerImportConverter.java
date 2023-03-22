package com.shopapp.api.converter;

import com.shopapp.data.definition.CustomerReadable;
import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entity.AuthenticationType;
import com.shopapp.data.entity.CreditCardEntity;
import com.shopapp.data.entity.CustomerEntity;
import com.shopapp.shared.dto.CustomerDtoIn;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class CustomerImportConverter extends AbstractIdImportConverter<CustomerDtoIn, CustomerEntity> implements CustomerReadable {

    private final DataServiceProvider dataServiceProvider;

    @Override
    public String getEmail() {
        out.setEmail(in.getEmail());
        return in.getEmail();
    }

    @Override
    public String getEncryptedPassword() {
        return null;
    }

    @Override
    public String getEmailVerificationToken() {
        //nothing
        return null;
    }

    @Override
    public boolean isVerified() {
        return false;
    }

    @Override
    public AuthenticationType getAuthenticationType() {
        return null;
    }

    @Override
    public AddressEntity getAddress() {
        return null;
    }

    @Override
    public Set<CreditCardEntity> getCreditCards() {
        return null;
    }
}
