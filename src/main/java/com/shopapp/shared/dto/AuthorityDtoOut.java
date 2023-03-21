package com.shopapp.shared.dto;

import com.shopapp.api.converter.IdBasedResource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityDtoOut extends IdBasedResource {

    private String name;

}
