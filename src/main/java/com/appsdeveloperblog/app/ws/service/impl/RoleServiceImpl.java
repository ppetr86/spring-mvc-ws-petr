package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.repository.RoleRepository;
import com.appsdeveloperblog.app.ws.service.RoleService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl extends AbstractIdBasedServiceImpl<RoleEntity> implements RoleService<RoleEntity> {

    RoleRepository roleRepository;

    @Override
    public RoleRepository getRepository() {
        return this.roleRepository;
    }


    @Override
    public Class<RoleEntity> getPojoClass() {
        return RoleEntity.class;
    }

    @Override
    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name);
    }
}
