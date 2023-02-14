package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.repository.IdBasedRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface IdBasedService<T extends IdBasedEntity> {

    Long getCountDelayed();

    Long getCount();

    List<T> loadAll();

    T save(T obj);

    List<T> saveAll(Collection<T> values);

    List<T> loadAllDelayed();

    IdBasedRepository<T> getRepository();

    T loadById(UUID id);

    Class<T> getPojoClass();

    void onBeforeWrite(final T dbObj);

}
