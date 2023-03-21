package com.shopapp.shared.dto;

import com.shopapp.api.converter.IdBasedResource;
import com.shopapp.data.entitydto.ModelReference;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoleDtoOut extends IdBasedResource {

    private String name;

    private Set<ModelReference> authorities = new HashSet<>();


}
