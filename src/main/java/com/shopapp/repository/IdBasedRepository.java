package com.shopapp.repository;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface IdBasedRepository<T extends IdBasedEntity> extends
        JpaRepository<T, UUID>,
        JpaSpecificationExecutor<T> {
}
