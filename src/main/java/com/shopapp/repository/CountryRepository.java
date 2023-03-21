package com.shopapp.repository;

import com.shopapp.data.entity.CountryEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends IdBasedRepository<CountryEntity> {
}
