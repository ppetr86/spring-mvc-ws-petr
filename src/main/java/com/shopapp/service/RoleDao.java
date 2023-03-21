package com.shopapp.service;

import com.shopapp.data.entity.RoleEntity;
import com.shopapp.shared.Roles;

public interface RoleDao extends IdDao<RoleEntity> {

    RoleEntity findByName(Roles name);


}
