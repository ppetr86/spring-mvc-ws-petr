package com.shopapp.service.impl.superclass;

import com.shopapp.data.entity.superclass.IdBasedTimeEntity;
import com.shopapp.service.IdTimeDao;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public abstract class AbstractIdTimeDaoImpl<T extends IdBasedTimeEntity> extends AbstractIdDaoImpl<T> implements IdTimeDao<T> {

    @Override
    public List<T> findByCreatedAt(final LocalDateTime createdAd) {
        if (createdAd == null)
            return Collections.emptyList();
        return this.getRepository().findByCreatedAt(createdAd);
    }

    @Override
    public List<T> findByCreatedAtBetween(final LocalDateTime start, final LocalDateTime end) {
        if (start == null || end == null || start.isAfter(end))
            return Collections.emptyList();
        return this.getRepository().findByCreatedAtBetween(start, end);
    }

    @Override
    public List<T> findByUpdatedAt(final LocalDateTime updatedAt) {
        if (updatedAt == null)
            return Collections.emptyList();
        return this.getRepository().findByUpdatedAt(updatedAt);
    }

    @Override
    public List<T> findByUpdatedAtBetween(final LocalDateTime start, final LocalDateTime end) {
        if (start == null || end == null || start.isAfter(end))
            return Collections.emptyList();
        return this.getRepository().findByUpdatedAtBetween(start, end);
    }
}
