package com.shopapp.data.entitydto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PasswordResetRequestDto {
    @Email
    private String email;
}
