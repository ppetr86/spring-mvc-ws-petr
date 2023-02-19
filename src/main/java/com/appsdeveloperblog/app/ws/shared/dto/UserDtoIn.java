package com.appsdeveloperblog.app.ws.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoIn implements Serializable {

    @Serial
    private static final long serialVersionUID = 6835192601898364280L;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String emailVerificationToken;
    private List<AddressDtoIn> addresses;
}
