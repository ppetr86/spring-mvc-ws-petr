package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.ProductEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.ProductSnapshotEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.UserSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.ProductRepository;
import com.appsdeveloperblog.app.ws.service.ProductService;
import com.appsdeveloperblog.app.ws.service.ProductSnapshotService;
import com.appsdeveloperblog.app.ws.service.UserSnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedTimeRevisionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceImpl extends AbstractIdBasedTimeRevisionServiceImpl<ProductEntity> implements ProductService {


    private ProductRepository productRepository;

    private final ProductSnapshotService productSnapshotService;

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
            this.productSnapshotService.save(snapshot);
        }
    }

    @Override
    public ProductRepository getRepository() {
        return this.productRepository;
    }

    @Override
    public Class<ProductEntity> getPojoClass() {
        return ProductEntity.class;
    }
}
