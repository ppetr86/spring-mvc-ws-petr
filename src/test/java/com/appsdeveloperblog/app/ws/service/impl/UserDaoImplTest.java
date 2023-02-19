package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.exceptions.InvalidParameterException;
import com.appsdeveloperblog.app.ws.repository.UserRepository;
import com.appsdeveloperblog.app.ws.shared.AmazonSES;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.UserDtoIn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDaoImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    UserDaoImpl userService;

    UserEntity userEntity;

    UserDtoIn userDtoIn;

    String encryptedPassword = "74hghd8474jf";

    @Test
    void createUser_duplicateEmail() {
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);
        Assertions.assertThrows(RuntimeException.class, () -> userService.createUser(new UserDtoIn()));
    }

    @Test
    void deleteUser() {
    }

    List<AddressDtoIn> getAddressesDto() {
        AddressDtoIn addressDto = new AddressDtoIn();
        addressDto.setCity("Vancouver");
        addressDto.setCountry("Canada");
        addressDto.setPostalCode("ABC123");
        addressDto.setStreet("123 Street name");

        AddressDtoIn billingAddressDto = new AddressDtoIn();
        billingAddressDto.setCity("Vancouver");
        billingAddressDto.setCountry("Canada");
        billingAddressDto.setPostalCode("ABC123");
        billingAddressDto.setStreet("123 Street name");

        List<AddressDtoIn> addresses = new ArrayList<>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);

        return addresses;

    }

    @Test
    void getUser() {
        var userFromRepo = new UserEntity("A", "B", "test@test.com",
                "123", "456",
                true, Collections.emptySet(), Collections.emptySet(), Collections.emptySet());

        when(userRepository.findByEmail(anyString())).thenReturn(userFromRepo);

        var userdto = userService.findByEmail("test@test.com");
        assertNotNull(userdto);
        assertEquals("A", userdto.getFirstName());
    }

    @Test
    void getUserByUserId() {
    }

    @Test
    void getUser_UsernameNotFound() {
        when(userRepository.findByEmail("")).thenReturn(null);
        Assertions.assertThrows(InvalidParameterException.class, () -> userService.findByEmail(""));
    }

    @Test
    void getUsers() {
    }

    @Test
    void loadUserByUsername() {
    }

    @Test
    void requestPasswordReset() {
    }

    @Test
    void resetPassword() {
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        userEntity = new UserEntity( "Petr",
                "Novotny", "test@test.com", encryptedPassword, "456",
                true, Collections.emptySet(), Collections.emptySet(), Collections.emptySet());
        userEntity.setId(UUID.randomUUID());

        userDtoIn = new UserDtoIn("Petr", "Novotny",
                "test@test.com", encryptedPassword, "456", Collections.emptyList());

    }

    @Test
    final void testCreateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDtoIn userDtoIn = new UserDtoIn();
        List<AddressDtoIn> addressesDto = getAddressesDto();
        userDtoIn.setAddresses(addressesDto);
        userDtoIn.setFirstName("user_from_test");
        userDtoIn.setLastName("user_from_test");
        userDtoIn.setPassword("12345678");
        userDtoIn.setEmail("user_from_test@test.com");

        var storedUserDetails = userService.createUser(userDtoIn);

        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertNotNull(storedUserDetails.getId());
        assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());

        //verify(utils,times(storedUserDetails.getAddresses().size())).generateAddressId(anyInt());
        verify(bCryptPasswordEncoder, times(1)).encode("12345678");
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void updateUser() {
    }

    @Test
    void verifyEmailToken() {
    }
}