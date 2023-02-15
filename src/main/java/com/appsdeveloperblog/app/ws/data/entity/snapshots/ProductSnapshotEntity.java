package com.appsdeveloperblog.app.ws.data.entity.snapshots;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "products_snapshots")
@NoArgsConstructor
@Getter
@Setter
public class ProductSnapshotEntity extends IdBasedTimeSnapshotEntity implements Serializable {

    @Column(length = 255, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String alias;

    @Column(length = 512, nullable = false, name = "short_description")
    private String shortDescription;

    @Column(length = 4096, nullable = false, name = "full_description")
    private String fullDescription;


    public ProductSnapshotEntity(UUID id) {
        this.id = id;
    }

    public ProductSnapshotEntity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + "]";
    }


}
