package com.appsdeveloperblog.app.ws.service;


import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.exceptions.InvalidParameterException;
import com.appsdeveloperblog.app.ws.shared.dto.UserDtoIn;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService, IdTimeRevisionDao<UserEntity> {

    UserEntity createUser(UserDtoIn user);


    void deleteUser(UUID id);


    boolean existsByEmail(String email);


    UserEntity findByEmail(String email) throws InvalidParameterException, UsernameNotFoundException;


    UserEntity findByUserId(UUID id);


    UserEntity findOneBy(String firstName, String lastName, String email, Boolean isVerified);


    List<UserEntity> getUsers();


    List<UserEntity> getUsers(int page, int limit);


    List<UserEntity> getUsers(int page, int limit, String firstName, String lastName, String email, Boolean isVerified, boolean isAsc, String... sortByProperties);


    boolean requestPasswordReset(String email);


    boolean resetPassword(String token, String password);


    UserEntity updateUser(UUID id, UserDtoIn dto);


    boolean verifyEmailToken(String token);

}
