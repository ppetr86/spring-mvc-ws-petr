package com.shopapp.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoIn implements Serializable {

    @Serial
    private static final long serialVersionUID = 6835192601898364280L;

    @Email
    private String email;

    @NotNull
    @Size(min = 8, message = "{validation.password.size.too_short}")
    private String password;

    @NotNull
    private AddressDtoIn address;

    @NotNull
    private Set<CreditCardDtoIn> creditCards;

    private String emailVerificationToken;
}
