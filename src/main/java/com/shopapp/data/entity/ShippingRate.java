package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "shipping_rates")
@NoArgsConstructor
@Getter
@Setter
public class ShippingRate extends IdBasedEntity implements Serializable{
	
	private float rate;
	
	private int days;

	@Column(name = "cod_supported")
	private boolean codSupported;

	@ManyToOne
	@JoinColumn(name = "country_id")
	private CountryEntity country;

	@Column(nullable = false, length = 45)
	private String state;

	@Override
	public String toString() {
		return "ShippingRate [id=" + id + ", rate=" + rate + ", days=" + days + ", codSupported=" + codSupported
				+ ", country=" + country + ", state=" + state + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

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
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}