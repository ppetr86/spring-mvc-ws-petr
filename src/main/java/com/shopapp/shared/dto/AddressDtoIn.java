package com.shopapp.shared.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @Min(3)
    protected String firstName;

    @NotNull
    @Min(3)
    protected String lastName;

    @NotNull
    @Min(3)
    protected String phoneNumber;

    @NotNull
    @Min(3)
    protected String addressLine1;

    @NotNull
    @Min(3)
    protected String addressLine2;

    @NotNull
    @Min(3)
    protected String city;

    @NotNull
    @Min(3)
    protected String postalCode;

    protected boolean defaultForShipping;

    @NotNull
    @Min(3)
    protected String country;

}
