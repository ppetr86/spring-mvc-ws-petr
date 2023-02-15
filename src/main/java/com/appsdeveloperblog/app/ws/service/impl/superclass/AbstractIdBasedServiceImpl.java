package com.appsdeveloperblog.app.ws.service.impl.superclass;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.service.IdBasedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.appsdeveloperblog.app.ws.shared.Utils.delay;

@Service
public abstract class AbstractIdBasedServiceImpl<T extends IdBasedEntity> implements IdBasedService<T> {

    @Override
    public Optional<T> findOneBy(Specification<T> specification) {
        return this.getRepository().findOne(specification);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCount() {
        return this.getRepository().count();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getCountDelayed() {
        delay(1000);
        return this.getRepository().count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> loadAll() {
        return this.getRepository().findAll();
    }

    @Transactional(readOnly = true)
    public List<T> loadAll(Specification<T> specification) {
        if (specification == null)
            return this.getRepository().findAll();

        return this.getRepository().findAll(specification);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<T> loadAll(Specification<T> specification, Pageable pageRequest) {
        return this.getRepository().findAll(specification, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> loadAllDelayed() {
        delay(1000);
        return this.getRepository().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public T loadById(final UUID id) {
        if (id == null) {
            return null;
        }

        return getRepository().findById(id).orElse(null);
    }

    @Override
    public void onBeforeWrite(T dbObj) {
        //nothing
    }

    @Override
    @Transactional(readOnly = false)
    public T save(final T dbObj) {
        Assert.notNull(dbObj, "obj must not be null");
        return getRepository().save(dbObj);
    }

    @Override
    @Transactional(readOnly = false)
    public List<T> saveAll(Collection<T> values) {
        return this.getRepository().saveAll(values);
    }
}
