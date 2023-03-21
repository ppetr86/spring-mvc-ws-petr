package com.shopapp.shared.dto;

import com.shopapp.api.converter.IdBasedResource;
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
    private String id;
    protected String firstName;
    protected String lastName;
    protected String phoneNumber;
    protected String addressLine1;
    protected String addressLine2;
    protected String city;
    protected String state;
    protected String postalCode;
    protected boolean defaultForShipping;
    protected CountryDtoOut country;

}