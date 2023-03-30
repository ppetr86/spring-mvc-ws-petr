package com.shopapp.service;

import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entitydto.in.AddressDtoIn;

import java.util.List;
import java.util.UUID;

public interface AddressDao extends IdDao<AddressEntity> {

    AddressEntity createAddress(AddressDtoIn addressDtoIn);

    List<AddressEntity> getAddresses(int page, int limit, String city, String country, String streetName, String postalCode);

    AddressEntity updateAddressByAddressId(UUID addressId, AddressDtoIn dto);

}
