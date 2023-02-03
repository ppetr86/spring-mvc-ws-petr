package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.io.entity.address.AddressEntity;
import com.appsdeveloperblog.app.ws.io.repository.AddressRepository;
import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoOut;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    private UserRepository userRepository;

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

        Specification<AddressEntity> addressIdSpec = ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.like(root.get("addressId"), addressId);
        });

        Specification<AddressEntity> citySpec =
                (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("city"), city);

        Specification<AddressEntity> countrySpec =
                (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("country"), country);

        Specification<AddressEntity> streetNameSpec =
                (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("streetName"), streetName);

        Specification<AddressEntity> postalCodeSpec =
                (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("postalCode"), postalCode);

        Pageable pageable = PageRequest.of(page - 1, limit);

        Specification<AddressEntity> findAllSpecification =  Specification.where(addressIdSpec)
                .and(citySpec)
                .and(countrySpec)
                .and(streetNameSpec)
                .and(postalCodeSpec);
        Page<AddressEntity> foundBySpec = addressRepository.findAll(pageable);

        var returnValue = new ArrayList<AddressDtoOut>();
        Type listType = new TypeToken<List<AddressDtoOut>>() {
        }.getType();
        returnValue = new ModelMapper().map(foundBySpec.get(), listType);

        return returnValue;
    }


}
