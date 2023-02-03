package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoOut;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AddressService {

    List<AddressDtoIn> getAddresses(String userId);

    AddressDtoIn getAddress(String addressId);

    AddressDtoOut findByAddressId(String addressId);

    AddressDtoOut createAddress(AddressDtoIn addressDtoIn);

    AddressDtoOut updateAddressByAddressId(String addressId, AddressDtoIn dto);

    boolean deleteAddressByAddressId(String addressId);

    List<AddressDtoOut> getAddresss(int page, int limit, String addressId, String city, String country, String streetName, String postalCode);
}
