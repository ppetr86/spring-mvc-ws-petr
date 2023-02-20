package com.appsdeveloperblog.app.ws.api.converter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseResource implements ApiHasId {

    private String id;

}
