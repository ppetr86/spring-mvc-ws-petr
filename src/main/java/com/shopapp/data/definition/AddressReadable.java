package com.shopapp.data.definition;

import com.shopapp.data.entity.UserEntity;

public interface AddressReadable {

    String getAddressLine1();

    String getAddressLine2();

    String getCity();

    String getCountry();

    String getFirstName();

    String getLastName();

    String getPhoneNumber();

    String getPostalCode();

    UserEntity getUser();

    boolean isDefaultForShipping();
}
