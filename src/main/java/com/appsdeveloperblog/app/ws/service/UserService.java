package com.appsdeveloperblog.app.ws.service;


import com.appsdeveloperblog.app.ws.shared.dto.UserDtoIn;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDtoIn createUser(UserDtoIn user);
    UserDtoIn getUser(String email);

    UserDtoIn getUserByUserId(String id);

    UserDtoIn updateUser(String id, UserDtoIn dto);
    void deleteUser(String userId);

    List<UserDtoIn> getUsers(int page, int limit);

    boolean verifyEmailToken(String token);

    boolean requestPasswordReset(String email);

    boolean resetPassword(String token, String password);
}
