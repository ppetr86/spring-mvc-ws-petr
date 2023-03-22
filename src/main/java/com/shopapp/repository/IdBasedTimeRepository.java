package com.shopapp.repository;

import com.shopapp.data.entity.superclass.IdBasedTimeEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDateTime;
import java.util.List;

@NoRepositoryBean
public interface IdBasedTimeRepository<T extends IdBasedTimeEntity> extends IdBasedRepository<T> {

    //@Query("select * from ")
    List<T> findByCreatedAt(LocalDateTime createdAt);

    List<T> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    //@Query("select * from ")
    List<T> findByUpdatedAt(LocalDateTime updatedAt);

    List<T> findByUpdatedAtBetween(LocalDateTime start, LocalDateTime end);
}
