package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeEntity;
import com.appsdeveloperblog.app.ws.repository.IdBasedTimeRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface IdBasedTimeService<T extends IdBasedTimeEntity> extends IdBasedService<T> {


    List<T> findByCreatedAt(LocalDateTime createAd);


    List<T> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);


    List<T> findByUpdatedAt(LocalDateTime updatedAt);


    List<T> findByUpdatedAtBetween(LocalDateTime start, LocalDateTime end);


    @Override
    IdBasedTimeRepository<T> getRepository();
}
