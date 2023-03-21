package com.shopapp.data.entitydto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CreditCardNumberValidityDto {

    private String creditCardNumber;

    private String expirationDate;
}
