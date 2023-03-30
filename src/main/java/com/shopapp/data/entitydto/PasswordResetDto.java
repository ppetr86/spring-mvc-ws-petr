package com.shopapp.data.entitydto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PasswordResetDto {

    @NotNull
    private String token;

    @NotNull
    private String password;
}
