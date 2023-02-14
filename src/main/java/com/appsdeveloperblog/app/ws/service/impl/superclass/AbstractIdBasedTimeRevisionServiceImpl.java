package com.appsdeveloperblog.app.ws.service.impl.superclass;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.appsdeveloperblog.app.ws.service.IdBasedTimeRevisionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public abstract class AbstractIdBasedTimeRevisionServiceImpl<T extends IdBasedTimeRevisionEntity> extends AbstractIdBasedTimeServiceImpl<T> implements IdBasedTimeRevisionService<T> {

    private static final Map<Class<?>, Long> revisionCache = new HashMap<>();

    @Override
    public long findMaxRevision() {

        final Class<?> cls = getPojoClass();

        if (revisionCache.containsKey(cls)) {
            return revisionCache.get(cls);
        }

        final Long maxRevision = this.getRepository().findMaxRevision();
        final long revision = (maxRevision == null) ? 0L : maxRevision;
        revisionCache.put(cls, revision);
        return revision;
    }

    @Override
    public Long getNewRevision() {
        Long maxRevision = this.getRepository().findMaxRevision();
        return maxRevision == null ? 1 : maxRevision + 1;
    }

    @Override
    public T save(T dbObj) {

        dbObj.setRevision(getNewRevision());
        this.onBeforeWrite(dbObj);

        return super.save(dbObj);
    }
}
