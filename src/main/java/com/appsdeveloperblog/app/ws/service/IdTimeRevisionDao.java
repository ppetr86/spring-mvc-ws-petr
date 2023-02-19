package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.appsdeveloperblog.app.ws.repository.IdBasedTimeRevisionRepository;

public interface IdTimeRevisionDao<T extends IdBasedTimeRevisionEntity> extends IdTimeDao<T> {

    long findMaxRevision();


    Long getNewRevision();


    @Override
    IdBasedTimeRevisionRepository<T> getRepository();


}
