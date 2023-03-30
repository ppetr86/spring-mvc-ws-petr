package com.shopapp.data.entitydto.out;

import com.shopapp.data.entity.CreditCardEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDtoOut {

    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    public CreditCardDtoOut(CreditCardEntity entity) {
        this.creditCardNumber = entity.getCreditCardNumber();
        this.cvv = entity.getCvv();
        this.expirationDate = entity.getExpirationDate();
    }
}
