package com.appsdeveloperblog.app.ws.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDtoIn {
    private String city;
    private String country;
    private String street;
    private String postalCode;
}
