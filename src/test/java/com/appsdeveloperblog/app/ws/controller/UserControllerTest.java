package com.appsdeveloperblog.app.ws.controller;

import com.appsdeveloperblog.app.ws.api.controller.UserController;
import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.service.impl.UserDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserDaoImpl userService;

    UserEntity userDtoIn;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        userDtoIn = new UserEntity();
        userDtoIn.setFirstName("Sergey");
        userDtoIn.setLastName("Kargopolov");
        userDtoIn.setEmail("test@test.com");
        userDtoIn.setVerified(Boolean.FALSE);
        userDtoIn.setEmailVerificationToken(null);
        userDtoIn.setAddresses(getAddressesDto());
        userDtoIn.setEncryptedPassword("xcf58tugh47");

    }

    @Test
    final void testGetUser() {
        when(userService.findByUserId(UUID.randomUUID())).thenReturn(userDtoIn);

        var userRest = userController.getUser(UUID.randomUUID().toString());

        assertNotNull(userRest);
        assertEquals(UUID.randomUUID().toString(), userRest.getId());
        assertEquals(userDtoIn.getFirstName(), userRest.getFirstName());
        assertEquals(userDtoIn.getLastName(), userRest.getLastName());
        assertTrue(userDtoIn.getAddresses().size() == userRest.getAddresses().size());
    }


    private Set<AddressEntity> getAddressesDto() {
        var addressDto = new AddressEntity();
        addressDto.setCity("Vancouver");
        addressDto.setCountry("Canada");
        addressDto.setPostalCode("ABC123");
        addressDto.setStreet("123 Street name");

        var billingAddressDto = new AddressEntity();
        billingAddressDto.setCity("Vancouver");
        billingAddressDto.setCountry("Canada");
        billingAddressDto.setPostalCode("ABC123");
        billingAddressDto.setStreet("123 Street name");

        Set<AddressEntity> addresses = new HashSet<>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);

        return addresses;

    }

}
