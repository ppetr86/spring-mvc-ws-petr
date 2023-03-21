package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedTimeRevisionEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@Getter
@Setter
public class CustomerEntity extends IdBasedTimeRevisionEntity implements Serializable{

	@Column(nullable = false, unique = true, length = 45)
	private String email;

	@Column(nullable = false)
	private String encryptedPassword;

	private String emailVerificationToken;

	@Column(nullable = false)
	private boolean isVerified;

	@Column(name = "created_time")
	private Date createdTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "authentication_type", length = 10)
	private AuthenticationType authenticationType;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	private AddressEntity address;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "customer")
	private Set<CreditCardEntity> creditCards = new HashSet<>();

	/*@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinTable(name = "customers_addresses",
			joinColumns = @JoinColumn(name = "customer", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "address", referencedColumnName = "id"))
	private Set<AddressEntity> addresses = new HashSet<>();*/

	/*public void addAddress(AddressEntity value) {
		if (value != null && !this.addresses.contains(value)) {
			this.addresses.add(value);
			value.getCustomers().add(this);
		}
	}*/

	public CustomerEntity(UUID id) {
		this.id = id;
	}

	/*public void removeAddress(AddressEntity value) {
		if (value != null && this.addresses.contains(value)) {
			this.addresses.remove(value);
			value.setCustomers(null);
		}
	}*/

	public void addCreditCard(CreditCardEntity value) {
		if (value != null && !this.creditCards.contains(value)) {
			this.creditCards.add(value);
			value.setCustomer(this);
		}
	}
	
	public void removeCreditCard(CreditCardEntity value) {
		if (value != null && this.creditCards.contains(value)) {
			this.creditCards.remove(value);
			value.setCustomer(null);
		}
	}
	
	public String getFullName() {
		return this.address.firstName + " " + this.address.lastName;
	}
	
	@Transient
	public String getAddressToString() {
		String address = this.address.firstName;

		if (this.address.lastName != null && !this.address.lastName.isEmpty()) address += " " + this.address.lastName;

		if (!this.address.addressLine1.isEmpty()) address += ", " + this.address.addressLine1;

		if (this.address.addressLine2 != null && !this.address.addressLine2.isEmpty()) address += ", " + this.address.addressLine2;

		if (!this.address.city.isEmpty()) address += ", " + this.address.city;

		address += ", " + this.address.country;

		if (!this.address.postalCode.isEmpty()) address += ". Postal Code: " + this.address.postalCode;
		if (!this.address.phoneNumber.isEmpty()) address += ". Phone Number: " + this.address.phoneNumber;

		return address;
	}
}
