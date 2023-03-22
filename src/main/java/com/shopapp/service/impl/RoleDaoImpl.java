package com.shopapp.service.impl;

import com.shopapp.data.entity.RoleEntity;
import com.shopapp.repository.RoleRepository;
import com.shopapp.service.RoleDao;
import com.shopapp.service.impl.superclass.AbstractIdDaoImpl;
import com.shopapp.shared.Roles;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleDaoImpl extends AbstractIdDaoImpl<RoleEntity> implements RoleDao {

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
