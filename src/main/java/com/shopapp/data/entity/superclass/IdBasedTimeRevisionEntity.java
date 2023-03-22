package com.shopapp.data.entity.superclass;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class IdBasedTimeRevisionEntity extends IdBasedTimeEntity {

    private Long revision;

    protected IdBasedTimeRevisionEntity(final UUID id) {
        super(id);
    }

    public IdBasedTimeRevisionEntity(UUID id, Long revision, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, createdAt, updatedAt);
        this.revision = revision;
    }

    public IdBasedTimeRevisionEntity() {
        super();
    }
}
