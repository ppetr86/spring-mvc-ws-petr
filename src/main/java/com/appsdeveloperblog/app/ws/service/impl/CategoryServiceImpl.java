package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.CategoryEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.CategorySnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.CategoryRepository;
import com.appsdeveloperblog.app.ws.service.CategoryService;
import com.appsdeveloperblog.app.ws.service.CategorySnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedTimeRevisionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryServiceImpl extends AbstractIdBasedTimeRevisionServiceImpl<CategoryEntity> implements CategoryService {


    private CategoryRepository categoryRepository;

    private final CategorySnapshotService categorySnapshotService;

    @Override
    public Class<CategoryEntity> getPojoClass() {
        return CategoryEntity.class;
    }

    @Override
    public CategoryRepository getRepository() {
        return this.categoryRepository;
    }

    @Override
    public void onBeforeWrite(CategoryEntity dbObj) {
        super.onBeforeWrite(dbObj);

        final boolean dbObjExists = dbObj != null && dbObj.getId() != null && loadById(dbObj.getId()) != null;

        if (dbObjExists) {
            var snapshot = new CategorySnapshotEntity();
            snapshot.setMaxRevision(this.findMaxRevision());
            snapshot.setName(dbObj.getName());
            snapshot.setAlias(dbObj.getAlias());
            snapshot.setImage(dbObj.getImage());
            snapshot.setEnabled(dbObj.isEnabled());
            this.categorySnapshotService.save(snapshot);
        }
    }
}
