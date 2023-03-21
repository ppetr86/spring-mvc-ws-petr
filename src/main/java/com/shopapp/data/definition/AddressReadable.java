package com.shopapp.data.definition;

import com.shopapp.data.entity.CustomerEntity;

public interface AddressReadable {

    CustomerEntity getCustomer();

    boolean isDefaultForShipping();

    String getCountry();

    String getFirstName();

    String getLastName();

    String getPhoneNumber();

    String getAddressLine1();

    String getAddressLine2();

    String getCity();

    String getPostalCode();
}
