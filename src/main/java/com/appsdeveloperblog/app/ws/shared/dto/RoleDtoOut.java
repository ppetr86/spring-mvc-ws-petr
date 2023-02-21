package com.appsdeveloperblog.app.ws.shared.dto;

import com.appsdeveloperblog.app.ws.api.converter.IdBasedResource;
import com.appsdeveloperblog.app.ws.data.entitydto.ModelReference;
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
