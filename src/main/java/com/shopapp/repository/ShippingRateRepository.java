package com.shopapp.repository;

import com.shopapp.data.entity.ShippingRate;

public interface ShippingRateRepository extends IdBasedRepository<ShippingRate> {

    ShippingRate findByCountryAndState(String country, String state);

}