package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IdBasedTimeRevisionRepository<T extends IdBasedTimeRevisionEntity> extends IdBasedTimeRepository<T> {

    @Query("SELECT MAX(revision) from #{#entityName}")
    Long findMaxRevision();
}
