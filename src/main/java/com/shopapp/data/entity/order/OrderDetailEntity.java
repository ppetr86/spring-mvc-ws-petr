package com.shopapp.data.entity.order;

import com.shopapp.data.entity.CategoryEntity;
import com.shopapp.data.entity.product.ProductEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "order_details")
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailEntity extends IdBasedEntity implements Serializable {

    private int quantity;
    private float productCost;
    private float shippingCost;
    private float unitPrice;
    private float subtotal;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public OrderDetailEntity(String categoryName, int quantity, float productCost, float shippingCost, float subtotal) {
        this.product = new ProductEntity();
        this.product.setCategory(new CategoryEntity(categoryName));
        this.quantity = quantity;
        this.productCost = productCost * quantity;
        this.shippingCost = shippingCost;
        this.subtotal = subtotal;
    }

    public OrderDetailEntity(int quantity, String productName, float productCost, float shippingCost, float subtotal) {
        this.product = new ProductEntity(productName);
        this.quantity = quantity;
        this.productCost = productCost * quantity;
        this.shippingCost = shippingCost;
        this.subtotal = subtotal;
    }
}
