package com.shopapp.data.definition;

import com.shopapp.data.entity.CustomerEntity;

public interface AddressWritable {

    void setCustomer(CustomerEntity customer);

    void setDefaultForShipping(boolean defaultForShipping);

    void setCountry(String country);

    void setFirstName(String value);

    void setLastName(String value);

    void setPhoneNumber(String value);

    void setAddressLine1(String value);

    void setAddressLine2(String value);

    void setCity(String value);

    void setPostalCode(String value);
}
