package com.shopapp.data.entity;

import com.shopapp.shared.dto.AddressDtoIn;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
@Setter
public class AddressEntity extends AbstractAddressWithCountry implements Serializable{

	/*@ManyToMany(mappedBy = "addresses", fetch = FetchType.LAZY)
	private Set<CustomerEntity> customers = new HashSet<>();*/

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private CustomerEntity customer;

	@Column(name = "default_address")
	private boolean defaultForShipping;

	public AddressEntity(AddressDtoIn in) {
		this.firstName = in.getFirstName();
		this.lastName=in.getLastName();
		this.phoneNumber=in.getPhoneNumber();
		this.addressLine1=in.getAddressLine1();
		this.addressLine2=in.getAddressLine2();
		this.city=in.getCity();
		this.state=in.getState();
		this.postalCode=in.getPostalCode();
		this.defaultForShipping=in.isDefaultForShipping();

		//TODO: create some import converter
		//this.country=new CountryEntity(in.getCountry());
	}
}
