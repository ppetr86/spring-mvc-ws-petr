package com.shopapp.shared.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDtoIn {

    @NotNull
    @Size(min = 5, message = "{validation.phoneNumber.size.too_short}")
    protected String phoneNumber;
    @NotNull
    @Size(min = 5, message = "{validation.addressLine1.size.too_short}")
    protected String addressLine1;
    @NotNull
    @Size(min = 5, message = "{validation.addressLine2.size.too_short}")
    protected String addressLine2;
    @NotNull
    @Size(min = 3, message = "{validation.city.size.too_short}")
    protected String city;
    @NotNull
    @Size(min = 3, message = "{validation.postalCode.size.too_short}")
    protected String postalCode;
    protected boolean defaultForShipping;
    @NotNull
    @Size(min = 3, message = "{validation.country.size.too_short}")
    protected String country;
    @NotNull
    @Size(min = 2, message = "{validation.firstName.size.too_short}")
    @Size(max = 50, message = "{validation.firstName.size.too_long}")
    private String firstName;
    @NotNull
    @Size(min = 2, message = "{validation.lastName.size.too_short}")
    @Size(max = 50, message = "{validation.lastName.size.too_long}")
    private String lastName;

}
