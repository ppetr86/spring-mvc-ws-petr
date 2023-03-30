package com.shopapp.data.entitydto.out;

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
public class AddressDtoOut extends AbstractIdBasedDtoOut {
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
