package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeEntity;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDateTime;
import java.util.List;

@NoRepositoryBean
public interface IdBasedTimeRepository<T extends IdBasedTimeEntity> extends IdBasedRepository<T> {

    //@Query("select * from ")
    List<T> findByCreatedAt(LocalDateTime createdAt);

    //@Query("select * from ")
    List<T> findByUpdatedAt(LocalDateTime updatedAt);

    List<T> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<T> findByUpdatedAtBetween(LocalDateTime start, LocalDateTime end);
}
