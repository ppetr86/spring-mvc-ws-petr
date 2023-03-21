package com.shopapp.service.impl;

import com.shopapp.data.entity.CountryEntity;
import com.shopapp.repository.CountryRepository;
import com.shopapp.repository.IdBasedRepository;
import com.shopapp.service.CountryDao;
import com.shopapp.service.impl.superclass.AbstractIdDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CountryDaoImpl extends AbstractIdDaoImpl<CountryEntity> implements CountryDao {

    private CountryRepository countryRepository;

    @Override
    public Class<CountryEntity> getPojoClass() {
        return CountryEntity.class;
    }

    @Override
    public IdBasedRepository<CountryEntity> getRepository() {
        return countryRepository;
    }

}
