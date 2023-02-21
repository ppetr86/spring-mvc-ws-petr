package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.shared.Roles;

public interface RoleDao extends IdDao<RoleEntity> {

    RoleEntity findByName(Roles name);


}
