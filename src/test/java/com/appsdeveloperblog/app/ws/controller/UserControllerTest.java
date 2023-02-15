package com.appsdeveloperblog.app.ws.controller;

import com.appsdeveloperblog.app.ws.api.controller.UserController;
import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserEntity userDtoIn;

    final String USER_ID = "bfhry47fhdjd7463gdh";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userDtoIn = new UserEntity();
        userDtoIn.setFirstName("Sergey");
        userDtoIn.setLastName("Kargopolov");
        userDtoIn.setEmail("test@test.com");
        userDtoIn.setVerified(Boolean.FALSE);
        userDtoIn.setEmailVerificationToken(null);
        userDtoIn.setUserId(USER_ID);
        userDtoIn.setAddresses(getAddressesDto());
        userDtoIn.setEncryptedPassword("xcf58tugh47");

    }

    @Test
    final void testGetUser() {
        when(userService.findByUserId(anyString())).thenReturn(userDtoIn);

        var userRest = userController.getUser(USER_ID);

        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals(userDtoIn.getFirstName(), userRest.getFirstName());
        assertEquals(userDtoIn.getLastName(), userRest.getLastName());
        assertTrue(userDtoIn.getAddresses().size() == userRest.getAddresses().size());
    }


    private Set<AddressEntity> getAddressesDto() {
        var addressDto = new AddressEntity();
        addressDto.setCity("Vancouver");
        addressDto.setCountry("Canada");
        addressDto.setPostalCode("ABC123");
        addressDto.setStreetName("123 Street name");

        var billingAddressDto = new AddressEntity();
        billingAddressDto.setCity("Vancouver");
        billingAddressDto.setCountry("Canada");
        billingAddressDto.setPostalCode("ABC123");
        billingAddressDto.setStreetName("123 Street name");

        Set<AddressEntity> addresses = new HashSet<>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);

        return addresses;

    }

}
