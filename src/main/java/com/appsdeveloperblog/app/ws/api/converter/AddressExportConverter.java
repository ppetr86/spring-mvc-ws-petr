package com.appsdeveloperblog.app.ws.api.converter;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoOut;

public class AddressExportConverter extends AbstractIdConverter<AddressEntity, AddressDtoOut> {


    @Override
    public AddressDtoOut convertToDtoOut(AddressEntity source) {
        var target = new AddressDtoOut();
        setSourcePropertiesToTarget(source, target);
        return target;
    }

    @Override
    public AddressDtoOut convertToDtoOutUsingModelReference(AddressEntity source) {
        var target = new AddressDtoOut();
        setSourcePropertiesToTarget(source, target);
        return target;
    }

    @Override
    public void setSourcePropertiesToTarget(final AddressEntity source, final AddressDtoOut target) {
        if (source.getId() != null) target.setId(source.getId().toString());
        if (source.getStreet() != null) target.setStreet(source.getStreet());
        if (source.getCity() != null) target.setCity(source.getCity());
        if (source.getPostalCode() != null) target.setPostalCode(source.getPostalCode());
    }
}
