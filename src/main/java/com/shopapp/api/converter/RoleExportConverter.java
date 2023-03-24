package com.shopapp.api.converter;

import com.shopapp.api.controller.RoleController;
import com.shopapp.data.entity.RoleEntity;
import com.shopapp.shared.dto.RoleDtoOut;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.UUID;

public class RoleExportConverter extends AbstractIdExportConverter<RoleEntity, RoleDtoOut> {
    @Override
    public RoleDtoOut convertToDtoOut(final RoleEntity source) {
        var target = new RoleDtoOut();
        setSourcePropertiesToTarget(source, target);
        target.add(createSelfLink(source.getId()));
        return target;
    }

    @Override
    public RoleDtoOut convertToDtoOutUsingModelReferenceForChildEntities(final RoleEntity source) {
        var target = convertToDtoOut(source);

        var authorityConverter = new AuthorityExportConverter();
        // target.setAuthorities(authorityConverter.convertIdBasedEntitiesToModelReference(source.getAuthorities()));
        return target;
    }

    @Override
    public Link createSelfLink(UUID id) {
        return createSelfLink(id.toString());
    }

    @Override
    public Link createSelfLink(final String id) {
        if (id == null) return null;
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).getRoleById(id)).withSelfRel();
    }

    @Override
    public AbstractIdExportConverter<RoleEntity, RoleDtoOut> getConverter() {
        return this;
    }

    @Override
    public void setSourcePropertiesToTarget(final RoleEntity source, final RoleDtoOut target) {
        if (source.getId() != null) target.setId(source.getId().toString());
        if (source.getName() != null) target.setName(source.getName().name());
    }
}
