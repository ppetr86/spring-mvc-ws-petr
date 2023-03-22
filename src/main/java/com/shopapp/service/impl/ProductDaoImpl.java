package com.shopapp.service.impl;

import com.shopapp.data.entity.product.ProductEntity;
import com.shopapp.data.entity.snapshots.ProductSnapshotEntity;
import com.shopapp.repository.ProductRepository;
import com.shopapp.service.ProductDao;
import com.shopapp.service.ProductSnapshotDao;
import com.shopapp.service.impl.superclass.AbstractIdTimeRevisionDaoImpl;
import com.shopapp.service.specification.GenericSpecification;
import com.shopapp.service.specification.GenericSpecificationsBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductDaoImpl extends AbstractIdTimeRevisionDaoImpl<ProductEntity> implements ProductDao {

    private final ProductSnapshotDao productSnapshotDao;
    private ProductRepository productRepository;

    private Specification<ProductEntity> createNameSpecification(String name) {
        var specBuilder = new GenericSpecificationsBuilder<ProductEntity>();
        specBuilder.with("name", GenericSpecification.SearchOperation.EQUALITY, false, List.of(name));
        return specBuilder.build();
    }

    @Override
    public boolean existsByName(String name) {
        if (!StringUtils.hasText(name))
            return false;

        Specification<ProductEntity> spec = createNameSpecification(name);
        return this.getRepository().exists(spec);
    }

    @Override
    public ProductEntity findByName(String name) {
        if (!StringUtils.hasText(name))
            return null;
        Specification<ProductEntity> spec = createNameSpecification(name);
        return this.getRepository().findOne(spec).orElse(null);
    }

    @Override
    public Class<ProductEntity> getPojoClass() {
        return ProductEntity.class;
    }

    @Override
    public ProductRepository getRepository() {
        return this.productRepository;
    }

    @Override
    public void onBeforeWrite(ProductEntity dbObj) {
        super.onBeforeWrite(dbObj);

        final boolean dbObjExists = dbObj != null && dbObj.getId() != null && loadById(dbObj.getId()) != null;

        //
        if (dbObjExists) {
            var snapshot = new ProductSnapshotEntity();
            snapshot.setMaxRevision(this.findMaxRevision());
            snapshot.setName(dbObj.getName());
            snapshot.setAlias(dbObj.getAlias());
            snapshot.setShortDescription(dbObj.getShortDescription());
            snapshot.setFullDescription(dbObj.getFullDescription());
            this.productSnapshotDao.save(snapshot);
        }
    }
}
