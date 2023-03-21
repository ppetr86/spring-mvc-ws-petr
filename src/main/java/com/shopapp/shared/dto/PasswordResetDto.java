package com.shopapp.shared.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PasswordResetDto {
    private String token;
    private String password;
}
