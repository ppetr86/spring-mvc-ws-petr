package com.appsdeveloperblog.app.ws.data.entitydto;

import com.appsdeveloperblog.app.ws.api.converter.IdBasedResource;
import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;

import java.util.Objects;


public class ModelReference extends IdBasedResource {


    public ModelReference(IdBasedEntity obj) {
        if (obj.getId() != null)
            setId(obj.getId().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelReference that)) return false;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
