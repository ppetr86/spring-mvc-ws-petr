package com.shopapp.api.converter;

import com.shopapp.api.controller.UserController;
import com.shopapp.data.entity.UserEntity;
import com.shopapp.shared.dto.UserDtoOut;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class UserExportConverter extends AbstractIdConverter<UserEntity, UserDtoOut> {


    @Override
    public UserDtoOut convertToDtoOut(final UserEntity source) {
        var target = new UserDtoOut();
        setSourcePropertiesToTarget(source, target);
        target.add(createSelfLink(source.getId()));

        target.setRoles(new RoleExportConverter()
                .convertIdBasedEntitiesToModelReference(source.getRoles()));

        return target;
    }


    @Override
    public UserDtoOut convertToDtoOutUsingModelReferenceForChildEntities(final UserEntity source) {
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
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(id)).withSelfRel();
    }

    @Override
    public AbstractIdConverter<UserEntity, UserDtoOut> getConverter() {
        return this;
    }

    @Override
    public void setSourcePropertiesToTarget(final UserEntity source, final UserDtoOut target) {
        if (source.getId() != null)
            target.setId(source.getId().toString());

        if (source.getFirstName() != null)
            target.setFirstName(source.getFirstName());

        if (source.getLastName() != null)
            target.setLastName(source.getLastName());

        if (source.getEmail() != null)
            target.setEmail(source.getEmail());
    }
}
