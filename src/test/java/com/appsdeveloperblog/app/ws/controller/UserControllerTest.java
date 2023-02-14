package com.appsdeveloperblog.app.ws.controller;

import com.appsdeveloperblog.app.ws.service.impl.UserServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.AddressDtoIn;
import com.appsdeveloperblog.app.ws.shared.dto.UserDtoIn;
import com.appsdeveloperblog.app.ws.ui.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class UserControllerTest {

	@InjectMocks
    UserController userController;
	
	@Mock
    UserServiceImpl userService;
	
	UserDtoIn userDtoIn;
	
	final String USER_ID = "bfhry47fhdjd7463gdh";
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		userDtoIn = new UserDtoIn();
        userDtoIn.setFirstName("Sergey");
        userDtoIn.setLastName("Kargopolov");
        userDtoIn.setEmail("test@test.com");
        userDtoIn.setEmailVerificationStatus(Boolean.FALSE);
        userDtoIn.setEmailVerificationToken(null);
        userDtoIn.setUserId(USER_ID);
        userDtoIn.setAddresses(getAddressesDto());
        userDtoIn.setEncryptedPassword("xcf58tugh47");
		
	}

	@Test
	final void testGetUser() {
	    when(userService.getUserByUserId(anyString())).thenReturn(userDtoIn);
	    
	    var userRest = userController.getUser(USER_ID);
	    
	    assertNotNull(userRest);
	    assertEquals(USER_ID, userRest.getUserId());
	    assertEquals(userDtoIn.getFirstName(), userRest.getFirstName());
	    assertEquals(userDtoIn.getLastName(), userRest.getLastName());
	    assertTrue(userDtoIn.getAddresses().size() == userRest.getAddresses().size());
	}
	
	
	private List<AddressDtoIn> getAddressesDto() {
		AddressDtoIn addressDto = new AddressDtoIn();
		addressDto.setCity("Vancouver");
		addressDto.setCountry("Canada");
		addressDto.setPostalCode("ABC123");
		addressDto.setStreetName("123 Street name");

		AddressDtoIn billingAddressDto = new AddressDtoIn();
		billingAddressDto.setCity("Vancouver");
		billingAddressDto.setCountry("Canada");
		billingAddressDto.setPostalCode("ABC123");
		billingAddressDto.setStreetName("123 Street name");

		List<AddressDtoIn> addresses = new ArrayList<>();
		addresses.add(addressDto);
		addresses.add(billingAddressDto);

		return addresses;

	}

}
