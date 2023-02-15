package com.appsdeveloperblog.app.ws.data.entitydto;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModelReference {

    private String uuid;

    public ModelReference(IdBasedEntity obj) {
        this.uuid = obj.getId().toString();
    }
}
