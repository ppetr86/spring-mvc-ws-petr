package com.appsdeveloperblog.app.ws.shared.dto;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//representationmodel is for hateoas support
public class AddressDtoOut extends RepresentationModel<AddressDtoOut> {
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;

    public AddressDtoOut(AddressEntity each) {
        this.addressId = each.getAddressId();
        this.city = each.getCity();
        this.country = each.getCountry();
        this.streetName = each.getStreetName();
        this.postalCode = each.getPostalCode();
        ;
    }
}
