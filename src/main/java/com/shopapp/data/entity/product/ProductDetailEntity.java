package com.shopapp.data.entity.product;

import com.shopapp.data.entity.superclass.IdBasedTimeRevisionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
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
    @JoinColumn(name = "product")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDetailEntity that)) return false;
        if (!super.equals(o)) return false;
        return super.equalsId(o) || name.equals(that.name) && value.equals(that.value) && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(name, value, product);
    }
}
