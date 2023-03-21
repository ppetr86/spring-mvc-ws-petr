package com.shopapp.shared.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class CustomerDtoIn implements Serializable {

    @Serial
    private static final long serialVersionUID = 6835192601898364280L;

    @NotNull
    @Max(45)
    private String email;

    @NotNull
    @Min(8)
    private String password;

    @NotNull
    private AddressDtoIn address;
}
