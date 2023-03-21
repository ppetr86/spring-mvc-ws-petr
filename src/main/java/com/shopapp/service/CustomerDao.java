package com.shopapp.service;

import com.shopapp.data.entity.CustomerEntity;
import com.shopapp.shared.dto.CustomerDtoIn;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface CustomerDao extends UserDetailsService, IdDao<CustomerEntity> {

    CustomerEntity createCustomer(CustomerDtoIn user);

    void deleteCustomer(UUID id);

    List<CustomerEntity> getCustomers();

    List<CustomerEntity> getCustomers(int page, int limit);


    List<CustomerEntity> getCustomers(int page, int limit, String firstName, String lastName, String email, Boolean isVerified, boolean isAsc, String... sortByProperties);

    boolean requestPasswordReset(String email);


    boolean resetPassword(String token, String password);

    CustomerEntity updateCustomer(UUID id, CustomerDtoIn dto);


    boolean verifyEmailToken(String token);
}
