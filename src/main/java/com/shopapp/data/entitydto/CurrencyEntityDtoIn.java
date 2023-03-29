package com.shopapp.data.entitydto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CurrencyEntityDtoIn {
    private String name;

    private String symbol;

    private String code;

}
