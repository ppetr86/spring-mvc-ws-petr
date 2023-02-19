package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.data.entity.BrandEntity;

public interface BrandService extends IdTimeRevisionDao<BrandEntity> {

    boolean existsByName(String brand);


    BrandEntity findByName(String brand);
}
