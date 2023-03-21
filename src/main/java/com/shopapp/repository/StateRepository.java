package com.shopapp.repository;


import com.github.javafaker.Country;
import com.shopapp.data.entity.State;

import java.util.List;

public interface StateRepository extends IdBasedRepository<State> {

	public List<State> findByCountryOrderByNameAsc(Country country);
}
