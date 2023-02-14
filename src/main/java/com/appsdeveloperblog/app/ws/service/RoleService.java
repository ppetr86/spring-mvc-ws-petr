package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;

public interface RoleService<T extends IdBasedEntity> extends IdBasedService<RoleEntity> {

    RoleEntity findByName(String name);


}
