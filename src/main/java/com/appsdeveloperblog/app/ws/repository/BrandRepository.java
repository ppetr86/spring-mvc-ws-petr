package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.BrandEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends IdBasedTimeRevisionRepository<BrandEntity> {
}
