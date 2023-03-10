package com.appsdeveloperblog.app.ws.api.converter;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.data.entitydto.ListEnvelope;
import com.appsdeveloperblog.app.ws.data.entitydto.ModelReference;
import org.springframework.hateoas.Link;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface Convertable<S extends IdBasedEntity, T extends IdBasedResource> {

    Set<ModelReference> convertIdBasedEntitiesToModelReference(final Collection<S> source);


    ModelReference convertIdBasedEntityToModelReference(final S source);


    T convertToDtoOut(final S source);


    ListEnvelope<T> convertToDtoOutEnvelope(final List<S> source);


    ListEnvelope<T> convertToDtoOutEnvelopeUsingModelReference(final List<S> source);


    T convertToDtoOutUsingModelReferenceForChildEntities(final S source);


    List<T> convertToListDtoOut(final List<S> source);


    Link createSelfLink(final UUID id);


    Link createSelfLink(final String id);


    AbstractIdConverter<S, T> getConverter();


    void setSourcePropertiesToTarget(final S source, final T target);


}
