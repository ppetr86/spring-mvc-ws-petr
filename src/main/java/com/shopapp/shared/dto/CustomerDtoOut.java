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
public class CustomerDtoOut extends IdBasedResource {

    private String id;
    private String email;
    private AddressDtoOut address;
}
