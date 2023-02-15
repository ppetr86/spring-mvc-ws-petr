package com.appsdeveloperblog.app.ws.api.converter;

import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.data.entitydto.ListEnvelope;
import com.appsdeveloperblog.app.ws.data.entitydto.ModelReference;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoOut;
import com.appsdeveloperblog.app.ws.shared.dto.UserDtoOut;

import java.util.List;
import java.util.stream.Collectors;

public class UserExportConverter {

    public UserDtoOut<AddressDtoOut> convertToDtoOut(final UserEntity user) {

        var result = new UserDtoOut<AddressDtoOut>();
        setUserParameters(user, result);
        result.setAddresses(user.getAddresses().stream()
                .map(AddressDtoOut::new)
                .collect(Collectors.toList()));

        return result;
    }

    public ListEnvelope<UserDtoOut<AddressDtoOut>> convertToDtoOutEnvelope(final List<UserEntity> users) {
        return new ListEnvelope<>(users.stream().map(this::convertToDtoOut).collect(Collectors.toList()));
    }

    public ListEnvelope<UserDtoOut<ModelReference>> convertToDtoOutEnvelopeUsingModelReference(final List<UserEntity> users) {
        return new ListEnvelope<>(users.stream().map(this::convertToDtoOutUsingModelReference).toList());
    }

    public UserDtoOut<ModelReference> convertToDtoOutUsingModelReference(final UserEntity user) {

        var result = new UserDtoOut<ModelReference>();
        result.setUserId(user.getUserId());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setEmail(user.getEmail());
        result.setAddresses(user.getAddresses().stream()
                .map(ModelReference::new)
                .collect(Collectors.toList()));

        return result;
    }

    private static void setUserParameters(UserEntity user, UserDtoOut<AddressDtoOut> result) {
        result.setUserId(user.getUserId());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setEmail(user.getEmail());
    }
}
