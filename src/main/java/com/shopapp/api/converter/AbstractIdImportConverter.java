package com.shopapp.api.converter;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import com.shopapp.shared.dto.InResource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractIdImportConverter<S extends InResource, T extends IdBasedEntity> {

    protected S in;

    protected T out;
}
