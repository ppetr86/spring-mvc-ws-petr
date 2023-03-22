package com.shopapp.repository;

import com.shopapp.data.entity.superclass.IdBasedTimeRevisionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IdBasedTimeRevisionRepository<T extends IdBasedTimeRevisionEntity> extends IdBasedTimeRepository<T> {

    @Query("SELECT MAX(revision) from #{#entityName}")
    Long findMaxRevision();
}
