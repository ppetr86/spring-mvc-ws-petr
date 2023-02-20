package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.repository.RoleRepository;
import com.appsdeveloperblog.app.ws.service.RoleDao;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdDaoImpl;
import com.appsdeveloperblog.app.ws.shared.Roles;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleDaoImpl extends AbstractIdDaoImpl<RoleEntity> implements RoleDao<RoleEntity> {

    RoleRepository roleRepository;


    @Override
    public RoleEntity findByName(Roles name) {
        if (name == null)
            return null;
        return getRepository().findByName(name);
    }

    @Override
    public Class<RoleEntity> getPojoClass() {
        return RoleEntity.class;
    }

    @Override
    public RoleRepository getRepository() {
        return this.roleRepository;
    }
}
