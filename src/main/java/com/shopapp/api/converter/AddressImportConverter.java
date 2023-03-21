package com.shopapp.api.converter;

import com.shopapp.data.definition.AddressReadable;
import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entity.CustomerEntity;
import com.shopapp.shared.dto.AddressDtoIn;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddressImportConverter implements AddressReadable {

    private final DataServiceProvider dataServiceProvider;

    private final AddressDtoIn dtoIn;

    private final CustomerEntity customer;

    private AddressEntity entity = new AddressEntity();


    @Override
    public CustomerEntity getCustomer() {
        this.entity.setCustomer(customer);
        return this.customer;
    }

    @Override
    public boolean isDefaultForShipping() {
        this.entity.setDefaultForShipping(dtoIn.isDefaultForShipping());
        return dtoIn.isDefaultForShipping();
    }

    @Override
    public String getCountry() {
        var countryString = this.dtoIn.getCountry();
        return dtoIn.getCountry();
    }

    @Override
    public String getFirstName() {
        this.entity.setFirstName(dtoIn.getFirstName());
        return dtoIn.getFirstName();
    }

    @Override
    public String getLastName() {
        this.entity.setLastName(dtoIn.getLastName());
        return dtoIn.getFirstName();
    }

    @Override
    public String getPhoneNumber() {
        this.entity.setPhoneNumber(dtoIn.getPhoneNumber());
        return dtoIn.getFirstName();
    }

    @Override
    public String getAddressLine1() {
        this.entity.setAddressLine1(dtoIn.getAddressLine1());
        return dtoIn.getFirstName();
    }

    @Override
    public String getAddressLine2() {
        this.entity.setAddressLine2(dtoIn.getAddressLine2());
        return dtoIn.getFirstName();
    }

    @Override
    public String getCity() {
        this.entity.setCity(dtoIn.getCity());
        return dtoIn.getFirstName();
    }

    @Override
    public String getPostalCode() {
        this.entity.setPostalCode(dtoIn.getPostalCode());
        return dtoIn.getFirstName();
    }
}
