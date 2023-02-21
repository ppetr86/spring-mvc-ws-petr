package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.repository.AuthorityRepository;
import com.appsdeveloperblog.app.ws.service.AuthorityDao;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdDaoImpl;
import com.appsdeveloperblog.app.ws.shared.Authority;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthorityDaoImpl extends AbstractIdDaoImpl<AuthorityEntity> implements AuthorityDao {

    private final AuthorityRepository authorityRepository;

    @Override
    public AuthorityEntity findByAuthority(Authority authority) {
        if (authority == null) return null;
        return getRepository().findByName(authority);
    }

    @Override
    public Class<AuthorityEntity> getPojoClass() {
        return AuthorityEntity.class;
    }

    @Override
    public AuthorityRepository getRepository() {
        return this.authorityRepository;
    }
}
