package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.shared.Authority;

public interface AuthorityDao extends IdDao<AuthorityEntity> {

    AuthorityEntity findByAuthority(Authority authority);

}
