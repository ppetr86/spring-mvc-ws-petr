package com.shopapp.data.entity.snapshots;

import com.shopapp.data.entity.superclass.IdBasedTimeEntity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class IdBasedTimeSnapshotEntity extends IdBasedTimeEntity {

    private Long maxRevision;

    public IdBasedTimeSnapshotEntity(UUID id) {
        super(id);
    }

    public IdBasedTimeSnapshotEntity() {
        super();
    }
}
