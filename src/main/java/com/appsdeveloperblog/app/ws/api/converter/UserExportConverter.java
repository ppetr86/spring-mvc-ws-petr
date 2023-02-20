package com.appsdeveloperblog.app.ws.api.converter;

import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.data.entitydto.ModelReference;
import com.appsdeveloperblog.app.ws.shared.dto.UserDtoOut;

import java.util.stream.Collectors;

public class UserExportConverter extends AbstractIdConverter<UserEntity, UserDtoOut> {


    @Override
    public UserDtoOut convertToDtoOut(final UserEntity source) {
        var target = new UserDtoOut();
        setSourcePropertiesToTarget(source, target);
        target.setAddresses(source.getAddresses().stream()
                .map(ModelReference::new)
                .collect(Collectors.toSet()));

        return target;
    }


    @Override
    public UserDtoOut convertToDtoOutUsingModelReference(final UserEntity source) {
        var target = new UserDtoOut();
        setSourcePropertiesToTarget(source, target);
        var addressConverter = new AddressExportConverter();
        target.setAddresses(addressConverter.convertIdBasedEntitiesToModelReference(source.getAddresses()));
        return target;
    }


    @Override
    public void setSourcePropertiesToTarget(final UserEntity source, final UserDtoOut target) {
        if (source.getId() != null)
            target.setId(source.getId().toString());

        if (source.getFirstName() != null)
            target.setFirstName(source.getFirstName());

        if (target.getLastName() != null)
            target.setLastName(source.getLastName());

        if (target.getEmail() != null)
            target.setEmail(source.getEmail());
    }
}
