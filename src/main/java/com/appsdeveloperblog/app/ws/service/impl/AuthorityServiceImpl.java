package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.repository.AuthorityRepository;
import com.appsdeveloperblog.app.ws.service.AuthorityService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthorityServiceImpl extends AbstractIdBasedServiceImpl<AuthorityEntity> implements AuthorityService {

    private final AuthorityRepository authorityRepository;


    @Override
    public AuthorityRepository getRepository() {
        return this.authorityRepository;
    }

    @Override
    public Class<AuthorityEntity> getPojoClass() {
        return AuthorityEntity.class;
    }

    @Override
    public AuthorityEntity findByName(String name) {
        return authorityRepository.findByName(name);
    }
}
