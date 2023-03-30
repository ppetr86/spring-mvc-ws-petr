package com.shopapp.service.impl;

import com.shopapp.data.entity.CurrencyEntity;
import com.shopapp.repository.CurrencyRepository;
import com.shopapp.service.CurrencyDao;
import com.shopapp.service.impl.superclass.AbstractIdDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrencyDaoImpl extends AbstractIdDaoImpl<CurrencyEntity> implements CurrencyDao {

    private final CurrencyRepository currencyRepository;

    @Override
    public Class<CurrencyEntity> getPojoClass() {
        return CurrencyEntity.class;
    }

    @Override
    public CurrencyRepository getRepository() {
        return this.currencyRepository;
    }

}
