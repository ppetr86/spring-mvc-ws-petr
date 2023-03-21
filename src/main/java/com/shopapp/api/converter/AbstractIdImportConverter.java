package com.shopapp.api.converter;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class AbstractIdImportConverter<S extends IdBasedEntity, T extends IdBasedResource> implements ExportConvertable<S, T> {


}
