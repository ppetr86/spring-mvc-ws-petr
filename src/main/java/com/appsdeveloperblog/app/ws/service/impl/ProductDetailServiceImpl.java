package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.ProductDetailEntity;
import com.appsdeveloperblog.app.ws.data.entity.ProductEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.ProductDetailSnapshotEntity;
import com.appsdeveloperblog.app.ws.data.entity.snapshots.ProductSnapshotEntity;
import com.appsdeveloperblog.app.ws.repository.ProductDetailRepository;
import com.appsdeveloperblog.app.ws.service.ProductDetailService;
import com.appsdeveloperblog.app.ws.service.ProductDetailSnapshotService;
import com.appsdeveloperblog.app.ws.service.ProductSnapshotService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdBasedTimeRevisionServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductDetailServiceImpl extends AbstractIdBasedTimeRevisionServiceImpl<ProductDetailEntity> implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;

    private final ProductDetailSnapshotService productDetailSnapshotService;

    @Override
    public void onBeforeWrite(ProductDetailEntity dbObj) {
        super.onBeforeWrite(dbObj);

        final boolean dbObjExists = dbObj != null && dbObj.getId() != null && loadById(dbObj.getId()) != null;

        if (dbObjExists) {
            var snapshot = new ProductDetailSnapshotEntity();
            snapshot.setMaxRevision(this.findMaxRevision());
            snapshot.setName(dbObj.getName());
            this.productDetailSnapshotService.save(snapshot);
        }
    }



    @Override
    public ProductDetailRepository getRepository() {
        return this.productDetailRepository;
    }

    @Override
    public Class<ProductDetailEntity> getPojoClass() {
        return ProductDetailEntity.class;
    }
}
