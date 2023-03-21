package com.shopapp.data.entity.product;


import com.shopapp.data.entity.superclass.IdBasedEntity;
import com.shopapp.shared.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "product_images")
@Getter
@Setter
public class ProductImageEntity extends IdBasedEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product")
    private ProductEntity product;

    public ProductImageEntity() {
        super();
    }

    public ProductImageEntity(String name, ProductEntity product) {
        this.name = name;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductImageEntity that)) return false;
        return super.equalsId(o) || name.equals(that.name) && product.equals(that.product);
    }

    @Transient
    public String getImagePath() {
        return Constants.S3_BASE_URI + "/product-images/" + product.getId() + "/extras/" + this.name;
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(name, product);
    }
}
