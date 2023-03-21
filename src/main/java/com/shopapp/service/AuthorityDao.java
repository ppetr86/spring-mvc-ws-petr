package com.shopapp.service;

import com.shopapp.data.entity.AuthorityEntity;
import com.shopapp.shared.Authority;

public interface AuthorityDao extends IdDao<AuthorityEntity> {

    AuthorityEntity findByAuthority(Authority authority);

}
