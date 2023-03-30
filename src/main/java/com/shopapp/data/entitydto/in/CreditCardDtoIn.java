package com.shopapp.data.entitydto.in;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardDtoIn extends AbstractIdBasedDtoIn {

    @NotNull
    private String creditCardNumber;

    @NotNull
    @Size(min = 3, message = "{validation.cvv.size.too_short}")
    private String cvv;

    @NotNull
    @Size(min = 5, message = "{validation.expirationDate.size.too_short}")
    @Size(max = 5, message = "{validation.expirationDate.size.too_long}")
    private String expirationDate;
}
