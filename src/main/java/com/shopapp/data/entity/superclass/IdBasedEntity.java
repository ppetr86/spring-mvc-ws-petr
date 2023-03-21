package com.shopapp.data.entity.superclass;

import com.shopapp.data.definition.UIdComparable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;


@MappedSuperclass
@Getter
@Setter
public abstract class IdBasedEntity implements Comparable<IdBasedEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    public IdBasedEntity(UUID id) {
        this.id = id;
    }


    public IdBasedEntity() {
        super();
    }

    @Override
    public int compareTo(final IdBasedEntity other) {
        return compareToId(other);
    }

    protected final int compareToId(final IdBasedEntity other) {
        return UIdComparable.ID_COMPARATOR.compare(this, other);
    }

    protected final boolean equalsId(final Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof IdBasedEntity)) return false;
        return UIdComparable.ID_COMPARATOR.compare(this, (IdBasedEntity) o) == 0;
    }

    protected final int hashCodeId() {
        return Objects.hashCode(this.id);
    }
}
