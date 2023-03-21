package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class AbstractAddress extends IdBasedEntity {

	@Column(name = "first_name", nullable = false, length = 45)
	protected String firstName;

	@Column(name = "last_name", nullable = false, length = 45)
	protected String lastName;

	@Column(name = "phone_number", nullable = false, length = 15)
	protected String phoneNumber;

	@Column(name = "address_line_1", nullable = false, length = 64)
	protected String addressLine1;

	@Column(name = "address_line_2", length = 64)
	protected String addressLine2;

	@Column(nullable = false, length = 45)
	protected String city;

	@Column(nullable = false, length = 45)
	protected String country;

	@Column(name = "postal_code", nullable = false, length = 10)
	protected String postalCode;

	@Override
	public String toString() {

		String address = firstName;

		if (lastName != null && !lastName.isEmpty()) address += " " + lastName;

		if (!addressLine1.isEmpty()) address += ", " + addressLine1;

		if (addressLine2 != null && !addressLine2.isEmpty()) address += ", " + addressLine2;

		if (!city.isEmpty()) address += ", " + city;

		if (!postalCode.isEmpty()) address += ". Postal Code: " + postalCode;
		if (!phoneNumber.isEmpty()) address += ". Phone Number: " + phoneNumber;

		return address;
	}
}
