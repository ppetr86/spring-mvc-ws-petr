package com.shopapp.data.entity.superclass;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class IdBasedTimeEntity extends IdBasedEntity {

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public IdBasedTimeEntity(UUID id) {
        super(id);
    }

    public IdBasedTimeEntity(UUID id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public IdBasedTimeEntity() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        IdBasedTimeEntity that = (IdBasedTimeEntity) o;
        return createdAt.equals(that.createdAt) && updatedAt.equals(that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), createdAt, updatedAt);
    }
}
