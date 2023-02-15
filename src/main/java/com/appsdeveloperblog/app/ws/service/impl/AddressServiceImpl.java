package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.repository.AddressRepository;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedServiceImpl;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecification;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecificationsBuilder;
import com.appsdeveloperblog.app.ws.service.specification.SpecificationFactory;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoOut;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImpl extends AbstractIdBasedServiceImpl<AddressEntity> implements AddressService {

    private AddressRepository addressRepository;

    private UserService userService;

    private SpecificationFactory<AddressEntity> addressSpecificationFactory;

    @Override
    public AddressDtoOut createAddress(final AddressDtoIn addressDtoIn) {
        var addressEntity = new AddressEntity(addressDtoIn);
        var persisted = addressRepository.save(addressEntity);
        return new AddressDtoOut(persisted);
    }

    @Override
    public boolean deleteAddressByAddressId(final String addressId) {
        return false;
    }

    @Override
    public AddressDtoOut findByAddressId(final String addressId) {
        var byAddressId = addressRepository.findByAddressId(addressId);
        return new AddressDtoOut(byAddressId);
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
    public List<AddressDtoOut> getAddress(final int page, final int limit, final String addressId, final String city,
                                          final String country, final String streetName, final String postalCode) {

        //https://medium.com/fleetx-engineering/searching-and-filtering-spring-data-jpa-specification-way-e22bc055229a
        var addressSpecBuilder = new GenericSpecificationsBuilder<AddressEntity>();

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

        Page<AddressEntity> foundBySpec = this.loadAll(
                addressSpecBuilder.build(),
                PageRequest.of(page - 1, limit));

        List<AddressDtoOut> returnValue = new ArrayList<>(foundBySpec.getSize());
        foundBySpec.get().forEach(each -> returnValue.add(new AddressDtoOut(each)));
        return returnValue;
    }

    @Override
    public List<AddressDtoIn> getAddresses(final String userId) {
        var returnValue = new ArrayList<AddressDtoIn>();

        var user = userService.findByUserId(userId);
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
    public Class<AddressEntity> getPojoClass() {
        return AddressEntity.class;
    }

    @Override
    public AddressRepository getRepository() {
        return this.addressRepository;
    }

    @Transactional
    @Override
    public AddressDtoOut updateAddressByAddressId(final String addressId, AddressDtoIn dto) {
        var foundByAddressid = addressRepository.findByAddressId(addressId);
        if (!dto.getCity().isBlank())
            foundByAddressid.setCity(dto.getCity());

        if (!dto.getPostalCode().isBlank())
            foundByAddressid.setPostalCode(dto.getPostalCode());

        if (!dto.getCountry().isBlank())
            foundByAddressid.setCountry(dto.getCountry());

        if (!dto.getStreetName().isBlank())
            foundByAddressid.setStreetName(dto.getStreetName());

        var patched = addressRepository.save(foundByAddressid);

        return new AddressDtoOut(patched);
    }


}
