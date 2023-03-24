package com.shopapp.service.impl.superclass;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import com.shopapp.service.IdDao;
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

import static com.shopapp.shared.Utils.delay;

@Service
public abstract class AbstractIdDaoImpl<T extends IdBasedEntity> implements IdDao<T> {

    @Override
    public void delete(UUID id) {
        this.getRepository().deleteById(id);
    }

    @Override
    public boolean exists(final Specification<T> spec) {
        return this.getRepository().exists(spec);
    }

    @Override
    public Optional<T> findOneBy(final Specification<T> specification) {
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
    @Transactional
    public List<T> loadAll() {
        return this.getRepository().findAll();
    }

    @Transactional
    public List<T> loadAll(final Specification<T> specification) {
        if (specification == null)
            return this.getRepository().findAll();

        return this.getRepository().findAll(specification);
    }

    @Override
    @Transactional
    public Page<T> loadAll(Pageable pageRequest) {
        return this.getRepository().findAll(pageRequest);
    }

    @Override
    @Transactional
    public Page<T> loadAll(final Specification<T> specification, final Pageable pageRequest) {
        return this.getRepository().findAll(specification, pageRequest);
    }

    @Override
    @Transactional
    public List<T> loadAllDelayed() {
        delay(1000);
        return this.getRepository().findAll();
    }

    @Override
    @Transactional
    public T loadById(final UUID id) {
        if (id == null) {
            return null;
        }

        return getRepository().findById(id).orElse(null);
    }

    @Override
    public void onBeforeWrite(final T dbObj) {
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
    public List<T> saveAll(final Collection<T> values) {
        return this.getRepository().saveAll(values);
    }
}
