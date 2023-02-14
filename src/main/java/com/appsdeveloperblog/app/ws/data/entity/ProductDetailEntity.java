package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "product_details")
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailEntity extends IdBasedTimeRevisionEntity implements Serializable {

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public ProductDetailEntity(UUID id, String name, String value, ProductEntity product) {
        super();
        this.id = id;
        this.name = name;
        this.value = value;
        this.product = product;
    }

    public ProductDetailEntity(String name, String value, ProductEntity product) {
        this.name = name;
        this.value = value;
        this.product = product;
    }

}
