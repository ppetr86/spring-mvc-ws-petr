package com.shopapp.data.entity;

import com.shopapp.data.definition.AddressDefinition;
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
public class AddressEntity extends AbstractAddress implements Serializable, AddressDefinition {

	/*@ManyToMany(mappedBy = "addresses", fetch = FetchType.LAZY)
	private Set<CustomerEntity> customers = new HashSet<>();*/

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId//You can use JPA’s @MapsId annotation to tell Hibernate that it shall use the foreign key of an associated
    // entity as the primary key.
    private UserEntity user;

    @Column(name = "default_address")
    private boolean defaultForShipping;

    public AddressEntity(AddressDtoIn in) {
        this.firstName = in.getFirstName();
        this.lastName = in.getLastName();
        this.phoneNumber = in.getPhoneNumber();
        this.addressLine1 = in.getAddressLine1();
        this.addressLine2 = in.getAddressLine2();
        this.city = in.getCity();
        this.postalCode = in.getPostalCode();
        this.defaultForShipping = in.isDefaultForShipping();
        this.country = in.getCountry();
    }
}
