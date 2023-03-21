package com.shopapp.shared.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CountryDtoIn {

    @NotNull
    @Min(3)
    private String name;

    @NotNull
    @Min(2)
    private String code;

    private Set<StateDtoIn> states;
}
