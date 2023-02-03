package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.shared.AmazonSES;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.UserDtoIn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    AmazonSES amazonSES;

    @InjectMocks
    UserServiceImpl userService;

    UserEntity userEntity;

    UserDtoIn userDtoIn;

    String userId = "hhty57ehfy";
    String encryptedPassword = "74hghd8474jf";


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        userEntity = new UserEntity(1L, userId, "Petr",
                "Novotny", "test@test.com", encryptedPassword, "456",
                true, Collections.emptyList());

        userDtoIn = new UserDtoIn(1l, userId, "Petr", "Novotny",
                "test@test.com", encryptedPassword, encryptedPassword, "456",
                true, Collections.emptyList());

    }

    @Test
    void createUser_duplicateEmail() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);
        Assertions.assertThrows(RuntimeException.class, () -> userService.createUser(new UserDtoIn()));
    }

    @Test
    final void testCreateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("hgfnghtyrir884");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDtoIn.class));

        UserDtoIn userDtoIn = new UserDtoIn();
        List<AddressDtoIn> addressesDto = getAddressesDto();
        userDtoIn.setAddresses(addressesDto);
        userDtoIn.setFirstName("Petr");
        userDtoIn.setLastName("Novotny");
        userDtoIn.setPassword("12345678");
        userDtoIn.setEmail("test@test.com");

        UserDtoIn storedUserDetails = userService.createUser(userDtoIn);

        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertNotNull(storedUserDetails.getUserId());
        assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());

        //verify(utils,times(storedUserDetails.getAddresses().size())).generateAddressId(anyInt());
        verify(bCryptPasswordEncoder, times(1)).encode("12345678");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    List<AddressDtoIn> getAddressesDto() {
        AddressDtoIn addressDto = new AddressDtoIn();
        addressDto.setType("shipping");
        addressDto.setCity("Vancouver");
        addressDto.setCountry("Canada");
        addressDto.setPostalCode("ABC123");
        addressDto.setStreetName("123 Street name");

        AddressDtoIn billingAddressDto = new AddressDtoIn();
        billingAddressDto.setType("billling");
        billingAddressDto.setCity("Vancouver");
        billingAddressDto.setCountry("Canada");
        billingAddressDto.setPostalCode("ABC123");
        billingAddressDto.setStreetName("123 Street name");

        List<AddressDtoIn> addresses = new ArrayList<>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);

        return addresses;

    }


    @Test
    void getUser() {
        var userFromRepo = new UserEntity(1L, "123", "A",
                "B", "test@test.com", "123", "456",
                true, Collections.emptyList());

        when(userRepository.findByEmail(anyString())).thenReturn(userFromRepo);

        var userdto = userService.getUser("test@test.com");
        assertNotNull(userdto);
        assertEquals("A", userdto.getFirstName());
    }

    @Test
    void getUser_UsernameNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.getUser("test@test.com"));
    }

    @Test
    void getUserByUserId() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUsers() {
    }

    @Test
    void verifyEmailToken() {
    }

    @Test
    void requestPasswordReset() {
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void resetPassword() {
    }
}