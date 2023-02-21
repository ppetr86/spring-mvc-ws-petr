package com.appsdeveloperblog.app.ws.api.converter;

import com.appsdeveloperblog.app.ws.api.controller.AddressController;
import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoOut;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class AddressExportConverter extends AbstractIdConverter<AddressEntity, AddressDtoOut> {


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
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AddressController.class).getAddressById(id)).withSelfRel();
    }

    @Override
    public AbstractIdConverter<AddressEntity, AddressDtoOut> getConverter() {
        return this;
    }

    @Override
    public void setSourcePropertiesToTarget(final AddressEntity source, final AddressDtoOut target) {
        if (source.getId() != null) target.setId(source.getId().toString());
        if (source.getCity() != null) target.setCity(source.getCity());
        if (source.getCountry() != null) target.setCountry(source.getCountry());
        if (source.getStreet() != null) target.setStreet(source.getStreet());
        if (source.getPostalCode() != null) target.setPostalCode(source.getPostalCode());
    }
}
