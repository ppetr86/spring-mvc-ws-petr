package com.appsdeveloperblog.app.ws.shared.dto;

import com.appsdeveloperblog.app.ws.api.converter.IdBasedResource;
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
    private String city;
    private String country;
    private String street;
    private String postalCode;

}
