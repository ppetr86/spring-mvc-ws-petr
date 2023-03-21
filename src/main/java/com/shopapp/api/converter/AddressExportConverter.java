package com.shopapp.api.converter;

import com.shopapp.api.controller.CustomerController;
import com.shopapp.data.entity.AddressEntity;
import com.shopapp.shared.dto.AddressDtoOut;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class AddressExportConverter extends AbstractIdExportConverter<AddressEntity, AddressDtoOut> {


    @Override
    public AddressDtoOut convertToDtoOut(final AddressEntity source) {
        var target = new AddressDtoOut();
        setSourcePropertiesToTarget(source, target);

        target.add(createSelfLink(source.getId()));
        return target;
    }

    @Override
    public AddressDtoOut convertToDtoOutUsingModelReferenceForChildEntities(final AddressEntity source) {
        //no child entities to convert
        return convertToDtoOut(source);
    }

    @Override
    public Link createSelfLink(final UUID id) {
        return createSelfLink(id.toString());
    }

    @Override
    public Link createSelfLink(final String id) {
        if (id == null) return null;
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomerAddress(id)).withSelfRel();
    }

    @Override
    public AbstractIdExportConverter<AddressEntity, AddressDtoOut> getConverter() {
        return this;
    }

    @Override
    public void setSourcePropertiesToTarget(final AddressEntity source, final AddressDtoOut target) {
        if (source.getId() != null)
            target.setId(source.getId().toString());
        if (source.getCity() != null)
            target.setCity(source.getCity());
        if (source.getCountry() != null)
            target.setCountry(source.getCountry());
        if (source.getAddressLine1() != null)
            target.setAddressLine1(source.getAddressLine1());
        if (source.getPostalCode() != null)
            target.setPostalCode(source.getPostalCode());
    }
}
