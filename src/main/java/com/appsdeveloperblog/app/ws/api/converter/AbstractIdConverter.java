package com.appsdeveloperblog.app.ws.api.converter;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.data.entitydto.ListEnvelope;
import com.appsdeveloperblog.app.ws.data.entitydto.ModelReference;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public abstract class AbstractIdConverter<S extends IdBasedEntity, T> implements Convertable<S, T> {

    @Override
    public final Set<ModelReference> convertIdBasedEntitiesToModelReference(final Collection<S> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptySet();
        }

        return source.stream()
                .filter(Objects::nonNull)
                .map(this::convertIdBasedEntityToModelReference)
                .collect(Collectors.toSet());
    }

    @Override
    public final ModelReference convertIdBasedEntityToModelReference(final S source) {
        if (source != null && source.getId() != null)
            return new ModelReference(source);
        return null;
    }

    public final ListEnvelope<T> convertToDtoOutEnvelope(final List<S> source) {
        if (source == null || source.isEmpty()) {
            return new ListEnvelope<>();
        }
        return new ListEnvelope<>(source.stream()
                .filter(Objects::nonNull)
                .map(this::convertToDtoOut)
                .collect(Collectors.toList()));
    }

    public final ListEnvelope<T> convertToDtoOutEnvelopeUsingModelReference(final List<S> source) {
        if (source == null || source.isEmpty()) {
            return new ListEnvelope<>();
        }
        return new ListEnvelope<>(source.stream()
                .filter(Objects::nonNull)
                .map(this::convertToDtoOutUsingModelReference)
                .collect(Collectors.toList()));
    }

    public final List<T> convertToListDtoOut(final List<S> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream().filter(Objects::nonNull).map(this::convertToDtoOut).toList();
    }
}
