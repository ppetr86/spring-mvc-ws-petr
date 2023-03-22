package com.shopapp.data.definition;

import com.shopapp.data.entity.UserEntity;

public interface AddressWritable {

    void setAddressLine1(String value);

    void setAddressLine2(String value);

    void setCity(String value);

    void setCountry(String country);

    void setDefaultForShipping(boolean defaultForShipping);

    void setFirstName(String value);

    void setLastName(String value);

    void setPhoneNumber(String value);

    void setPostalCode(String value);

    void setUser(UserEntity user);
}
