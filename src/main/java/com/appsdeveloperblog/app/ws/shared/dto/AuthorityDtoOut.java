package com.appsdeveloperblog.app.ws.shared.dto;

import com.appsdeveloperblog.app.ws.api.converter.IdBasedResource;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityDtoOut extends IdBasedResource {

    private String name;

}
