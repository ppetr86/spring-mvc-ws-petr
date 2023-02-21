package com.appsdeveloperblog.app.ws.api.converter;

import com.appsdeveloperblog.app.ws.api.controller.AuthorityController;
import com.appsdeveloperblog.app.ws.data.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.shared.dto.AuthorityDtoOut;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class AuthorityExportConverter extends AbstractIdConverter<AuthorityEntity, AuthorityDtoOut> {
    @Override
    public AuthorityDtoOut convertToDtoOut(AuthorityEntity source) {
        var target = new AuthorityDtoOut();
        setSourcePropertiesToTarget(source, target);
        return target;
    }

    @Override
    public AuthorityDtoOut convertToDtoOutUsingModelReferenceForChildEntities(AuthorityEntity source) {
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
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AuthorityController.class).getAuthorityById(id)).withSelfRel();
    }

    @Override
    public AbstractIdConverter<AuthorityEntity, AuthorityDtoOut> getConverter() {
        return this;
    }

    @Override
    public void setSourcePropertiesToTarget(AuthorityEntity source, AuthorityDtoOut target) {
        if (source.getId() != null) target.setId(source.getId().toString());
        if (source.getName() != null) target.setName(source.getName().name());
    }
}
