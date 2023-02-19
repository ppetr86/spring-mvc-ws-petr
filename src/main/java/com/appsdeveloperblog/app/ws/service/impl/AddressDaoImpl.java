package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.repository.AddressRepository;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdDaoImpl;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecificationsBuilder;
import com.appsdeveloperblog.app.ws.service.specification.SpecificationFactory;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AddressDaoImpl extends AbstractIdDaoImpl<AddressEntity> implements AddressService {

    private AddressRepository addressRepository;

    private SpecificationFactory<AddressEntity> addressSpecificationFactory;

    @Override
    public AddressEntity createAddress(final AddressDtoIn addressDtoIn) {
        var addressEntity = new AddressEntity(addressDtoIn);
        return addressRepository.save(addressEntity);
    }


    @Override
    public List<AddressEntity> getAddresses(final int page, final int limit, final String city,
                                            final String country, final String streetName,
                                            final String postalCode) {

        //https://medium.com/fleetx-engineering/searching-and-filtering-spring-data-jpa-specification-way-e22bc055229a
        var addressSpecBuilder = new GenericSpecificationsBuilder<AddressEntity>();

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

        Page<AddressEntity> foundBySpec = this.loadAll(
                addressSpecBuilder.build(),
                PageRequest.of(page - 1, limit));

        return foundBySpec.getContent();
    }


    @Override
    public Class<AddressEntity> getPojoClass() {
        return AddressEntity.class;
    }

    @Override
    public AddressRepository getRepository() {
        return this.addressRepository;
    }

    @Transactional
    @Override
    public AddressEntity updateAddressByAddressId(final UUID addressId, final AddressDtoIn dto) {
        var address = this.loadById(addressId);
        if (address == null) return null;

        if (!dto.getCity().isBlank())
            address.setCity(dto.getCity());

        if (!dto.getPostalCode().isBlank())
            address.setPostalCode(dto.getPostalCode());

        if (!dto.getCountry().isBlank())
            address.setCountry(dto.getCountry());

        if (!dto.getStreet().isBlank())
            address.setStreet(dto.getStreet());

        return addressRepository.save(address);
    }


}
