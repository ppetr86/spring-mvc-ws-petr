package com.appsdeveloperblog.app.ws.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoOut<T> {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<T> addresses;
}
