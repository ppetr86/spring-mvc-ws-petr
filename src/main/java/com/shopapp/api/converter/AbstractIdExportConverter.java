package com.shopapp.api.converter;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import com.shopapp.data.entitydto.ListEnvelope;
import com.shopapp.data.entitydto.ModelReference;
import com.shopapp.data.entitydto.out.AbstractIdBasedDtoOut;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.Link;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public abstract class AbstractIdExportConverter<S extends IdBasedEntity, T extends AbstractIdBasedDtoOut> implements ExportConvertable<S, T> {

    /*protected S source;
    protected T target;

    public AbstractIdExportConverter(Supplier<S> sourceSupplier, Supplier<T> targetSupplier) {
        this.source = sourceSupplier.get();
        this.target = targetSupplier.get();
    }*/

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
        if (source == null || source.getId() == null)
            return null;
        var modelReference = new ModelReference(source);
        modelReference.add(this.getConverter().createSelfLink(modelReference.getId()));
        return modelReference;
    }

    public final ListEnvelope<T> convertToDtoOutEnvelope(final List<S> source) {
        if (source == null || source.isEmpty()) {
            return new ListEnvelope<>();
        }
        return new ListEnvelope<>(source.stream()
                .filter(Objects::nonNull)
                .map(this::convertToDtoOut)
                .peek(createSelfLinkConsumer())
                .collect(Collectors.toList()));
    }

    public final ListEnvelope<T> convertToDtoOutEnvelopeUsingModelReference(final List<S> source) {
        if (source == null || source.isEmpty()) {
            return new ListEnvelope<>();
        }
        return new ListEnvelope<>(source.stream()
                .filter(Objects::nonNull)
                .map(this::convertToDtoOutUsingModelReferenceForChildEntities)
                .collect(Collectors.toList()));
    }

    public final List<T> convertToListDtoOut(final Collection<S> source) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        return source.stream()
                .filter(Objects::nonNull)
                .map(this::convertToDtoOut)
                .toList();
    }

    @Override
    public Link createSelfLink(UUID id) {
        //nothing
        return null;
    }

    private Consumer<T> createSelfLinkConsumer() {
        return each -> {
            Optional.ofNullable(this.getConverter()
                            .createSelfLink(each.getId()))
                    .ifPresent(each::add);
        };
    }
}
