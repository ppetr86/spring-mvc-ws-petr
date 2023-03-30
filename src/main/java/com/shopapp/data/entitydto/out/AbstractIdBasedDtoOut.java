package com.shopapp.data.entitydto.out;

import com.shopapp.api.converter.ApiHasId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractIdBasedDtoOut extends RepresentationModel<AbstractIdBasedDtoOut> implements ApiHasId {

    protected String id;

}
