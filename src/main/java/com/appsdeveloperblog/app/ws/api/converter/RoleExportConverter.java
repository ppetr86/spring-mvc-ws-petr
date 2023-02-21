package com.appsdeveloperblog.app.ws.api.converter;

import com.appsdeveloperblog.app.ws.api.controller.RoleController;
import com.appsdeveloperblog.app.ws.data.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.shared.dto.RoleDtoOut;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class RoleExportConverter extends AbstractIdConverter<RoleEntity, RoleDtoOut> {
    @Override
    public RoleDtoOut convertToDtoOut(final RoleEntity source) {
        var target = new RoleDtoOut();
        setSourcePropertiesToTarget(source, target);
        return target;
    }

    @Override
    public RoleDtoOut convertToDtoOutUsingModelReferenceForChildEntities(final RoleEntity source) {
        var target = convertToDtoOut(source);

        var authorityConverter = new AuthorityExportConverter();
        target.setAuthorities(authorityConverter.convertIdBasedEntitiesToModelReference(source.getAuthorities()));
        return target;
    }

    @Override
    public Link createSelfLink(final String id) {
        if (id == null) return null;
        return WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RoleController.class).getRoleById(id)).withSelfRel();
    }

    @Override
    public AbstractIdConverter<RoleEntity, RoleDtoOut> getConverter() {
        return this;
    }

    @Override
    public void setSourcePropertiesToTarget(final RoleEntity source, final RoleDtoOut target) {
        if (source.getId() != null) target.setId(source.getId().toString());
        if (source.getName() != null) target.setName(source.getName().name());
    }
}
