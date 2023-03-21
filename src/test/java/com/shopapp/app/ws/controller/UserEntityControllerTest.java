package com.shopapp.app.ws.controller;

import com.shopapp.api.controller.UserController;
import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entity.UserEntity;
import com.shopapp.service.impl.UserDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserEntityControllerTest {

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
    }


    private Set<AddressEntity> getAddressesDto() {
        var addressDto = new AddressEntity();
        addressDto.setCity("Vancouver");
        addressDto.setCountry(new CountryEntity("Canada", "CA"));
        addressDto.setPostalCode("ABC123");
        addressDto.setAddressLine1("123 Street name");

        var billingAddressDto = new AddressEntity();
        billingAddressDto.setCity("Vancouver");
        billingAddressDto.setCountry(new CountryEntity("Canada", "CA"));
        billingAddressDto.setPostalCode("ABC123");
        billingAddressDto.setAddressLine1("123 Street name");

        Set<AddressEntity> addresses = new HashSet<>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);

        return addresses;

    }

}
