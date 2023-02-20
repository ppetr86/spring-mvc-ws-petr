package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.repository.IdBasedRepository;

public interface PasswordTokenDao<T extends IdBasedEntity> extends IdDao<T> {

    @Override
    IdBasedRepository<T> getRepository();

}
