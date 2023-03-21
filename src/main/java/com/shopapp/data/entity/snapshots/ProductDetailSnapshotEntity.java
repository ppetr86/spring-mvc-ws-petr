package com.shopapp.data.entity.snapshots;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "product_details_snapshots")
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailSnapshotEntity extends IdBasedTimeSnapshotEntity implements Serializable {

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String value;


    public ProductDetailSnapshotEntity(UUID id, String name, String value) {
        super();
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public ProductDetailSnapshotEntity(String name, String value) {
        this.name = name;
        this.value = value;
    }

}
