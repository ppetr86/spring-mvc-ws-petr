package com.shopapp.service;

import com.shopapp.data.entity.BrandEntity;

public interface BrandDao extends IdTimeRevisionDao<BrandEntity> {

    boolean existsByName(String brand);


    BrandEntity findByName(String brand);
}
