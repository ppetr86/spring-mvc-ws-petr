package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.repository.IdBasedRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IdBasedService<T extends IdBasedEntity> {

    Long getCount();


    Long getCountDelayed();


    Class<T> getPojoClass();


    IdBasedRepository<T> getRepository();


    Optional<T> findOneBy(Specification<T> specification);


    List<T> loadAll();


    Page<T> loadAll(Specification<T> specification, Pageable pageRequest);


    List<T> loadAllDelayed();


    T loadById(UUID id);


    void onBeforeWrite(final T dbObj);


    T save(T obj);


    List<T> saveAll(Collection<T> values);

}
