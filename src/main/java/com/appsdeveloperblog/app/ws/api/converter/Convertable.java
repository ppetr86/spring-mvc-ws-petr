package com.appsdeveloperblog.app.ws.api.converter;

import com.appsdeveloperblog.app.ws.data.entitydto.ListEnvelope;
import com.appsdeveloperblog.app.ws.data.entitydto.ModelReference;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Convertable<S, T> {

    Set<ModelReference> convertIdBasedEntitiesToModelReference(Collection<S> source);


    ModelReference convertIdBasedEntityToModelReference(S source);


    T convertToDtoOut(final S source);


    ListEnvelope<T> convertToDtoOutEnvelope(final List<S> source);


    ListEnvelope<T> convertToDtoOutEnvelopeUsingModelReference(final List<S> source);


    T convertToDtoOutUsingModelReference(final S source);


    List<T> convertToListDtoOut(final List<S> source);


    void setSourcePropertiesToTarget(final S source, final T target);


}
