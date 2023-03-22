package com.shopapp.service;

import com.shopapp.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.shopapp.repository.IdBasedTimeRevisionRepository;

public interface IdTimeRevisionDao<T extends IdBasedTimeRevisionEntity> extends IdTimeDao<T> {

    long findMaxRevision();

    Long getNewRevision();

    @Override
    IdBasedTimeRevisionRepository<T> getRepository();

}
