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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoIn implements Serializable {

    @Serial
    private static final long serialVersionUID = 6835192601898364280L;

    @NotNull
    @Size(min = 2, message = "{validation.firstName.size.too_short}")
    @Size(max = 50, message = "{validation.firstName.size.too_long}")
    private String firstName;

    @NotNull
    @Size(min = 2, message = "{validation.lastName.size.too_short}")
    @Size(max = 50, message = "{validation.lastName.size.too_long}")
    private String lastName;

    @Email
    @Size(max = 120, message = "{validation.name.size.too_long}")
    private String email;

    @NotNull
    @Size(min = 8, message = "{validation.password.size.too_short}")
    @Size(max = 50, message = "{validation.password.size.too_long}")
    private String password;

    private String emailVerificationToken;
}
