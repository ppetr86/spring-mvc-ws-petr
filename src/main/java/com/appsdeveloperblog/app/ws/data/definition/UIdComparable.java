package com.appsdeveloperblog.app.ws.data.definition;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;

import java.util.Comparator;
import java.util.UUID;

public interface UIdComparable<T extends IdBasedEntity> extends Comparable<T> {
    Comparator<IdBasedEntity> ID_COMPARATOR = (o1, o2) -> {
        final UUID id1 = o1.getId();
        final UUID id2 = o2.getId();
        if (id1 == null || id2 == null)
            return (id1 == id2) ? 0 : (id1 == null) ? 1 : -1;
        return id1.compareTo(id2);
    };


    @Override
    boolean equals(Object o);


    @Override
    int hashCode();
}
