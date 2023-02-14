package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

@NoRepositoryBean
public interface IdBasedRepository<T extends IdBasedEntity> extends
        JpaRepository<T, UUID>,
        PagingAndSortingRepository<T, UUID>,
        CrudRepository<T, UUID>,
        JpaSpecificationExecutor<T> {
}
