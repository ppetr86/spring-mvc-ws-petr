package com.shopapp.shared.dto;

import com.shopapp.api.converter.IdBasedResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Schema(description = "RoleDtoOut Model Information")
@Getter
@Setter
@NoArgsConstructor
public class RoleDtoOut extends IdBasedResource {

    private String name;

    private Set<AuthorityDtoOut> authorities = new HashSet<>();

}
