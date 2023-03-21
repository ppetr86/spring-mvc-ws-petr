package com.shopapp.api.converter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public abstract class IdBasedResource extends RepresentationModel<IdBasedResource> implements ApiHasId {

    private String id;

}
