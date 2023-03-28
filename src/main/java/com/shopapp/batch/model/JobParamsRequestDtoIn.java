package com.shopapp.batch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class JobParamsRequestDtoIn {

    private String paramKey;
    private String paramValue;
}
