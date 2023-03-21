package com.shopapp.service.impl;

import com.shopapp.data.entity.AuthorityEntity;
import com.shopapp.repository.AuthorityRepository;
import com.shopapp.service.AuthorityDao;
import com.shopapp.service.impl.superclass.AbstractIdDaoImpl;
import com.shopapp.shared.Authority;
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
