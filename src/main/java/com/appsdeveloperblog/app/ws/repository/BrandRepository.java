package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.BrandEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends IdBasedTimeRevisionRepository<BrandEntity> {


    @Query("select c.brands from CategoryEntity c where c.name =:categoryName")
    List<BrandEntity> findBrandEntitiesByCategoryName(@Param("categoryName")String categoryName);
}
