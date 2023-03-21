package com.shopapp.service;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import com.shopapp.repository.IdBasedRepository;

public interface PasswordTokenDao<T extends IdBasedEntity> extends IdDao<T> {

    @Override
    IdBasedRepository<T> getRepository();

}
