package com.shopapp.service.impl;

import com.shopapp.data.entity.product.ProductDetailEntity;
import com.shopapp.data.entity.snapshots.ProductDetailSnapshotEntity;
import com.shopapp.repository.ProductDetailRepository;
import com.shopapp.service.ProductDetailDao;
import com.shopapp.service.ProductDetailSnapshotDao;
import com.shopapp.service.impl.superclass.AbstractIdTimeRevisionDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductDetailDaoImpl extends AbstractIdTimeRevisionDaoImpl<ProductDetailEntity> implements ProductDetailDao {

    private final ProductDetailRepository productDetailRepository;

    private final ProductDetailSnapshotDao productDetailSnapshotDao;

    @Override
    public Class<ProductDetailEntity> getPojoClass() {
        return ProductDetailEntity.class;
    }

    @Override
    public ProductDetailRepository getRepository() {
        return this.productDetailRepository;
    }

    @Override
    public void onBeforeWrite(ProductDetailEntity dbObj) {
        super.onBeforeWrite(dbObj);

        final boolean dbObjExists = dbObj != null && dbObj.getId() != null && loadById(dbObj.getId()) != null;

        if (dbObjExists) {
            var snapshot = new ProductDetailSnapshotEntity();
            snapshot.setMaxRevision(this.findMaxRevision());
            snapshot.setName(dbObj.getName());
            this.productDetailSnapshotDao.save(snapshot);
        }
    }
}
