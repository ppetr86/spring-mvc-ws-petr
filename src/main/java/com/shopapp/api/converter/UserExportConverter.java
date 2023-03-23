package com.shopapp.api.converter;

import com.shopapp.api.controller.UserController;
import com.shopapp.data.entity.UserEntity;
import com.shopapp.shared.dto.UserDtoOut;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class UserExportConverter extends AbstractIdExportConverter<UserEntity, UserDtoOut> {

    @Override
    public UserDtoOut convertToDtoOut(final UserEntity source) {
        var target = new UserDtoOut();
        setSourcePropertiesToTarget(source, target);
        target.add(createSelfLink(source.getId()));
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
    public AbstractIdExportConverter<UserEntity, UserDtoOut> getConverter() {
        return this;
    }

    @Override
    public void setSourcePropertiesToTarget(final UserEntity source, final UserDtoOut target) {
        if (source.getId() != null)
            target.setId(source.getId().toString());

        if (source.getEmail() != null)
            target.setEmail(source.getEmail());

        target.setVerified(source.isVerified());

        if (source.getPhotos() != null)
            target.setPhotos(source.getPhotos());

        if(source.getAddress()!=null)
            target.setAddress(new AddressExportConverter().convertToDtoOut(source.getAddress()));

        target.setRoles(new RoleExportConverter().convertToListDtoOut(source.getRoles()));
    }
}
