package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.BrandEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.BrandSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.BrandRepository;
import com.appsdeveloperblog.app.ws.service.BrandDao;
import com.appsdeveloperblog.app.ws.service.BrandSnapshotDao;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdTimeRevisionDaoImpl;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecification;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecificationsBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class BrandDaoImpl extends AbstractIdTimeRevisionDaoImpl<BrandEntity> implements BrandDao {

    private final BrandRepository brandRepository;

    private final BrandSnapshotDao brandSnapshotDao;

    @Override
    public boolean existsByName(String name) {
        if (!StringUtils.hasText(name))
            return false;

        Specification<BrandEntity> spec = createNameSpecification(name);
        return this.getRepository().exists(spec);
    }

    @Override
    public BrandEntity findByName(String name) {
        if (!StringUtils.hasText(name))
            return null;
        Specification<BrandEntity> spec = createNameSpecification(name);
        return this.getRepository().findOne(spec).orElse(null);
    }

    @Override
    public Class<BrandEntity> getPojoClass() {
        return BrandEntity.class;
    }

    @Override
    public BrandRepository getRepository() {
        return this.brandRepository;
    }

    @Override
    public void onBeforeWrite(BrandEntity dbObj) {
        super.onBeforeWrite(dbObj);

        final boolean dbObjExists = dbObj != null && dbObj.getId() != null && loadById(dbObj.getId()) != null;

        if (dbObjExists) {
            var snapshot = new BrandSnapshotEntity();
            snapshot.setMaxRevision(this.findMaxRevision());
            snapshot.setName(dbObj.getName());
            snapshot.setLogo(dbObj.getLogo());
            this.brandSnapshotDao.save(snapshot);
        }
    }

    private Specification<BrandEntity> createNameSpecification(String name) {
        var specBuilder = new GenericSpecificationsBuilder<BrandEntity>();
        specBuilder.with("name", GenericSpecification.SearchOperation.EQUALITY, false, List.of(name));
        return specBuilder.build();
    }
}
