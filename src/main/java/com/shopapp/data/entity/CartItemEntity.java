package com.shopapp.data.entity;

import com.shopapp.data.entity.product.ProductEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
public class CartItemEntity extends IdBasedEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private int quantity;

    @Transient
    private float shippingCost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItemEntity cartItem)) return false;
        return super.equalsId(o);
    }

    @Transient
    public float getSubtotal() {
        return product.getDiscountPrice() * quantity;
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(user, product, quantity, shippingCost);
    }

}
