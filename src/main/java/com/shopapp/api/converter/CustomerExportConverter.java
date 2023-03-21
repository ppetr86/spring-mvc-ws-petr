package com.shopapp.api.converter;

import com.shopapp.api.controller.CustomerController;
import com.shopapp.data.entity.CustomerEntity;
import com.shopapp.shared.dto.CustomerDtoOut;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class CustomerExportConverter extends AbstractIdConverter<CustomerEntity, CustomerDtoOut> {


    @Override
    public CustomerDtoOut convertToDtoOut(final CustomerEntity source) {
        var target = new CustomerDtoOut();
        setSourcePropertiesToTarget(source, target);
        target.add(createSelfLink(source.getId()));
        return target;
    }


    @Override
    public CustomerDtoOut convertToDtoOutUsingModelReferenceForChildEntities(final CustomerEntity source) {
        var target = convertToDtoOut(source);

        var addressConverter = new AddressExportConverter();
        return target;
    }

    @Override
    public Link createSelfLink(final UUID id) {
        return createSelfLink(id.toString());
    }

    @Override
    public Link createSelfLink(final String id) {
        if (id == null) return null;
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CustomerController.class).getCustomer(id)).withSelfRel();
    }

    @Override
    public AbstractIdConverter<CustomerEntity, CustomerDtoOut> getConverter() {
        return this;
    }

    @Override
    public void setSourcePropertiesToTarget(final CustomerEntity source, final CustomerDtoOut target) {
        if (source.getId() != null)
            target.setId(source.getId().toString());

        if (source.getAddress().getFirstName() != null)
            target.getAddress().setFirstName(source.getAddress().getFirstName());

        if (source.getAddress().getLastName() != null)
            target.getAddress().setLastName(source.getAddress().getLastName());

        if (source.getEmail() != null)
            target.setEmail(source.getEmail());
    }
}
