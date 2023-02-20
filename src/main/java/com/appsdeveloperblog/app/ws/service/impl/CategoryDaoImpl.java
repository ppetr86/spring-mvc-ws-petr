package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.CategoryEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.CategorySnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.CategoryRepository;
import com.appsdeveloperblog.app.ws.service.CategoryDao;
import com.appsdeveloperblog.app.ws.service.CategorySnapshotDao;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdTimeRevisionDaoImpl;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecification;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecificationsBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryDaoImpl extends AbstractIdTimeRevisionDaoImpl<CategoryEntity> implements CategoryDao {


    private CategoryRepository categoryRepository;

    private final CategorySnapshotDao categorySnapshotDao;

    @Override
    public CategoryEntity findByName(String categoryName) {
        if (categoryName == null || categoryName.isBlank())
            return null;

        var categorySpec = new GenericSpecificationsBuilder<CategoryEntity>();
        categorySpec.with("name", GenericSpecification.SearchOperation.EQUALITY, false, List.of(categoryName));
        return this.findOneBy(categorySpec.build()).orElse(null);
    }

    @Override
    public boolean existsByName(String name) {
        if (!StringUtils.hasText(name))
            return false;

        var categorySpec = new GenericSpecificationsBuilder<CategoryEntity>();
        categorySpec.with("name", GenericSpecification.SearchOperation.EQUALITY, false, List.of(name));
        return this.getRepository().exists(categorySpec.build());
    }

    @Override
    public Class<CategoryEntity> getPojoClass() {
        return CategoryEntity.class;
    }

    @Override
    public CategoryRepository getRepository() {
        return this.categoryRepository;
    }

    @Override
    @Transactional(readOnly = false)
    public void onBeforeWrite(CategoryEntity dbObj) {
        super.onBeforeWrite(dbObj);

        final boolean dbObjExists = dbObj != null && dbObj.getId() != null && loadById(dbObj.getId()) != null;

        if (dbObjExists) {
            var snapshot = new CategorySnapshotEntity();
            snapshot.setMaxRevision(dbObj.getRevision());
            snapshot.setName(dbObj.getName());
            snapshot.setAlias(dbObj.getAlias());
            snapshot.setImage(dbObj.getImage());
            snapshot.setEnabled(dbObj.isEnabled());
            this.categorySnapshotDao.save(snapshot);
        }
    }
}
