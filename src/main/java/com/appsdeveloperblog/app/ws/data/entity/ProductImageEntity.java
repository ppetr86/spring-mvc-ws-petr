package com.appsdeveloperblog.app.ws.data.entity;


import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.shared.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "product_images")
@Getter
@Setter
public class ProductImageEntity extends IdBasedEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public ProductImageEntity() {

    }

    public ProductImageEntity(UUID id, String name, ProductEntity product) {
        this.id = id;
        this.name = name;
        this.product = product;
    }

    public ProductImageEntity(String name, ProductEntity product) {
        this.name = name;
        this.product = product;
    }

    @Transient
    public String getImagePath() {
        return Constants.S3_BASE_URI + "/product-images/" + product.getId() + "/extras/" + this.name;
    }

}
