package com.shopapp.api.converter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class IdBasedResource extends RepresentationModel<IdBasedResource> implements ApiHasId {

    private String id;

}
