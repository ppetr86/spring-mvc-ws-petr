package com.appsdeveloperblog.app.ws.data.entity.snapshots;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "brands_snapshots")
@Getter
@Setter
public class BrandSnapshotEntity extends IdBasedTimeSnapshotEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -4280261731794140574L;

    @Column(nullable = false, length = 45, unique = true)
    private String name;

    @Column(nullable = false, length = 128)
    private String logo;


    public BrandSnapshotEntity(String name) {
        super();
        this.name = name;
        this.logo = "brand-logo.png";
    }

    public BrandSnapshotEntity(UUID id, String name) {
        super(id);
        this.name = name;
    }

    public BrandSnapshotEntity() {
        super();
    }

    @Override
    public String toString() {
        return "Brand [id=" + id + ", name=" + name + "]";
    }
}
