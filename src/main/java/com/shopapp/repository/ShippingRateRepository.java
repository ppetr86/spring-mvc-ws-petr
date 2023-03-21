package com.shopapp.repository;

import com.shopapp.data.entity.CountryEntity;
import com.shopapp.data.entity.ShippingRate;

public interface ShippingRateRepository extends IdBasedRepository<ShippingRate> {

	public ShippingRate findByCountryAndState(CountryEntity country, String state);
	
}