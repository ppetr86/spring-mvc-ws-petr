package com.shopapp.shared.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoIn implements Serializable {

    @Serial
    private static final long serialVersionUID = 6835192601898364280L;

    @NotNull
    @Max(50)
    private String firstName;
    @NotNull
    @Max(50)
    private String lastName;

    @Email
    @Max(120)
    private String email;

    @NotNull
    @Max(50)
    private String password;
    private String emailVerificationToken;
}
