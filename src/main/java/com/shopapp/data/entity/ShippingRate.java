package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "shipping_rates")
@NoArgsConstructor
@Getter
@Setter
public class ShippingRate extends IdBasedEntity implements Serializable {

    private float rate;

    private int days;

    @Column(name = "cod_supported")
    private boolean codSupported;

    @Column(unique = true, length = 45)
    private String country;

    @Column(nullable = false, length = 45)
    private String state;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShippingRate other = (ShippingRate) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ShippingRate [id=" + id + ", rate=" + rate + ", days=" + days + ", codSupported=" + codSupported
                + ", country=" + country + ", state=" + state + "]";
    }

}
