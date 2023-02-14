package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.appsdeveloperblog.app.ws.repository.IdBasedTimeRevisionRepository;

public interface IdBasedTimeRevisionService<T extends IdBasedTimeRevisionEntity> extends IdBasedTimeService<T> {

    long findMaxRevision();

    Long getNewRevision();

    @Override
    IdBasedTimeRevisionRepository<T> getRepository();


}
