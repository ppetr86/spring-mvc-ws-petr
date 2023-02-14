package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.BrandEntity;
import com.appsdeveloperblog.app.ws.data.entity.CategoryEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.BrandSnapshotEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.CategorySnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.BrandRepository;
import com.appsdeveloperblog.app.ws.service.BrandService;
import com.appsdeveloperblog.app.ws.service.BrandSnapshotService;
import com.appsdeveloperblog.app.ws.service.CategorySnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedTimeRevisionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandServiceImpl extends AbstractIdBasedTimeRevisionServiceImpl<BrandEntity> implements BrandService {

    private final BrandRepository brandRepository;

    private final BrandSnapshotService brandSnapshotService;

    @Override
    public void onBeforeWrite(BrandEntity dbObj) {
        super.onBeforeWrite(dbObj);

        final boolean dbObjExists = dbObj != null && dbObj.getId() != null && loadById(dbObj.getId()) != null;

        if (dbObjExists) {
            var snapshot = new BrandSnapshotEntity();
            snapshot.setMaxRevision(this.findMaxRevision());
            snapshot.setName(dbObj.getName());
            snapshot.setLogo(dbObj.getLogo());
            this.brandSnapshotService.save(snapshot);
        }
    }

    @Override
    public BrandRepository getRepository() {
        return this.brandRepository;
    }

    @Override
    public Class<BrandEntity> getPojoClass() {
        return BrandEntity.class;
    }
}
