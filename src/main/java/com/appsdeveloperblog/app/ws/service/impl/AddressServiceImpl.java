package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.io.entity.address.AddressEntity;
import com.appsdeveloperblog.app.ws.io.repository.AddressRepository;
import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecification;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecificationsBuilder;
import com.appsdeveloperblog.app.ws.service.specification.SpecificationFactory;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoOut;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    private UserRepository userRepository;

    private SpecificationFactory<AddressEntity> addressSpecificationFactory;

    @Override
    public List<AddressDtoIn> getAddresses(final String userId) {
        var returnValue = new ArrayList<AddressDtoIn>();

        var user = userRepository.findByUserId(userId);
        if (user == null)
            return returnValue;

        Iterable<AddressEntity> address = user.getAddresses();
        ModelMapper mapper = new ModelMapper();
        for (var aE : address) {
            returnValue.add(mapper.map(aE, AddressDtoIn.class));
        }

        return returnValue;
    }

    @Override
    public AddressDtoIn getAddress(final String addressId) {
        var entity = addressRepository.findByAddressId(addressId);
        var mapper = new ModelMapper();
        AddressDtoIn returnValue = null;
        if (entity != null) {
            returnValue = new ModelMapper().map(entity, AddressDtoIn.class);
        }

        return returnValue;
    }

    @Override
    public AddressDtoOut findByAddressId(final String addressId) {
        return null;
    }

    @Override
    public AddressDtoOut createAddress(final AddressDtoIn addressDtoIn) {
        return null;
    }

    @Override
    public AddressDtoOut updateAddressByAddressId(final String addressId, AddressDtoIn dto) {
        return null;
    }

    @Override
    public boolean deleteAddressByAddressId(final String addressId) {
        return false;
    }

    @Override
    public List<AddressDtoOut> getAddresss(final int page, final int limit, final String addressId, final String city,
                                           final String country, final String streetName, final String postalCode) {

        //https://medium.com/fleetx-engineering/searching-and-filtering-spring-data-jpa-specification-way-e22bc055229a
        GenericSpecificationsBuilder<AddressEntity> addressSpecBuilder = new GenericSpecificationsBuilder();

        if (!addressId.isBlank()) {
            addressSpecBuilder.with("addressId",
                    GenericSpecification.SearchOperation.LIKE,
                    true,
                    List.of(addressId));
        }

        if (!city.isBlank()) {
            addressSpecBuilder.with(addressSpecificationFactory.isLike("city", city));
        }

        if (!country.isBlank()) {
            addressSpecBuilder.with(addressSpecificationFactory.isLike("country", country));
        }

        if (!streetName.isBlank()) {
            addressSpecBuilder.with(addressSpecificationFactory.isLike("streetName", streetName));
        }

        if (!postalCode.isBlank()) {
            addressSpecBuilder.with(addressSpecificationFactory.isLike("postalCode", postalCode));
        }

        Page<AddressEntity> foundBySpec = addressRepository.findAll(
                addressSpecBuilder.build(),
                PageRequest.of(page - 1, limit));

        List<AddressDtoOut> returnValue = new ArrayList<>(foundBySpec.getSize());
        foundBySpec.get().forEach(each -> returnValue.add(new AddressDtoOut(each)));
        return returnValue;
    }


}
