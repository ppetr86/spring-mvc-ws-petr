package com.shopapp.shared.dto;

import com.shopapp.api.converter.IdBasedResource;
import com.shopapp.data.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//representationmodel is for hateoas support
public class AddressDtoOut extends IdBasedResource {
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String addressLine1;
    protected String addressLine2;
    protected String city;
    protected String state;
    protected String postalCode;
    protected boolean defaultForShipping;
    protected String country;
    private String id;

    public AddressDtoOut(AddressEntity value) {

        this.id = value.getId().toString();
        this.firstName = value.getFirstName();
        this.lastName = value.getLastName();

        this.phoneNumber = this.getPhoneNumber();
        this.addressLine1 = this.getAddressLine1();
        this.addressLine2 = this.getAddressLine2();
        this.city = this.getCity();
        this.state = this.getState();
        this.postalCode = this.getPostalCode();
        this.defaultForShipping = this.isDefaultForShipping();
        this.country = this.getCountry();
    }
}
