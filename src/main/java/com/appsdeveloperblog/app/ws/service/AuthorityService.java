package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.AuthorityEntity;

public interface AuthorityService extends IdBasedService<AuthorityEntity> {

    AuthorityEntity findByName(String name);

}
