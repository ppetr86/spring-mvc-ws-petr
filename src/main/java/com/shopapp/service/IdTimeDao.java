package com.shopapp.service;

import com.shopapp.data.entity.superclass.IdBasedTimeEntity;
import com.shopapp.repository.IdBasedTimeRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IdTimeDao<T extends IdBasedTimeEntity> extends IdDao<T> {

    List<T> findByCreatedAt(LocalDateTime createAd);

    List<T> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<T> findByUpdatedAt(LocalDateTime updatedAt);

    List<T> findByUpdatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Override
    IdBasedTimeRepository<T> getRepository();
}
