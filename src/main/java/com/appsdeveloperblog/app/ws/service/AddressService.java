package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoOut;

import java.util.List;

public interface AddressService extends IdBasedService<AddressEntity> {

    AddressDtoOut createAddress(AddressDtoIn addressDtoIn);


    boolean deleteAddressByAddressId(String addressId);


    AddressDtoOut findByAddressId(String addressId);


    AddressDtoIn getAddress(String addressId);


    List<AddressDtoOut> getAddress(int page, int limit, String addressId, String city, String country, String streetName, String postalCode);


    List<AddressDtoIn> getAddresses(String userId);


    AddressDtoOut updateAddressByAddressId(String addressId, AddressDtoIn dto);

}
