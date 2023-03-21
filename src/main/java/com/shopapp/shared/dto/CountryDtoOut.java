package com.shopapp.shared.dto;

import com.shopapp.api.converter.IdBasedResource;
import com.shopapp.data.entity.CountryEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryDtoOut  extends IdBasedResource {

    private String name;

    private String code;

    private Set<StateDtoOut> states;

    public CountryDtoOut(CountryEntity country) {
        this.setId(String.valueOf(country.getId()));
        this.name = country.getName();
        this.code = country.getCode();
        this.states = country.getStates().stream().map(each -> new StateDtoOut(each.getId(),each.getName())).collect(Collectors.toSet());
    }
}
