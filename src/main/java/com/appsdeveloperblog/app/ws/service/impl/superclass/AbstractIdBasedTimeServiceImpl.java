package com.appsdeveloperblog.app.ws.service.impl.superclass;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeEntity;
import com.appsdeveloperblog.app.ws.service.IdBasedTimeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public abstract class AbstractIdBasedTimeServiceImpl<T extends IdBasedTimeEntity> extends AbstractIdBasedServiceImpl<T> implements IdBasedTimeService<T> {

    @Override
    public List<T> findByCreatedAt(final LocalDateTime createAd) {
        return this.getRepository().findByCreatedAt(createAd);
    }

    @Override
    public List<T> findByUpdatedAt(final LocalDateTime updatedAt) {
        return this.getRepository().findByUpdatedAt(updatedAt);
    }

    @Override
    public List<T> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return this.getRepository().findByCreatedAtBetween(start, end);
    }

    @Override
    public List<T> findByUpdatedAtBetween(LocalDateTime start, LocalDateTime end) {
        return this.getRepository().findByUpdatedAtBetween(start, end);
    }
}
