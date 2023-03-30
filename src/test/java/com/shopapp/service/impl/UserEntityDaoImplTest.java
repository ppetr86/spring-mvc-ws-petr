package com.shopapp.service.impl;

import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entity.UserEntity;
import com.shopapp.data.entitydto.in.AddressDtoIn;
import com.shopapp.data.entitydto.in.UserDtoIn;
import com.shopapp.exceptions.InvalidParameterException;
import com.shopapp.repository.UserRepository;
import com.shopapp.shared.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserEntityDaoImplTest {

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
        //addressDto.setCountry(new CountryEntity("Canada", "CA"));
        addressDto.setPostalCode("ABC123");
        addressDto.setAddressLine1("123 Street name");

        AddressDtoIn billingAddressDto = new AddressDtoIn();
        billingAddressDto.setCity("Vancouver");
        //billingAddressDto.setCountry(new CountryEntity("Canada", "CA"));
        billingAddressDto.setPostalCode("ABC123");
        billingAddressDto.setAddressLine1("123 Street name");

        List<AddressDtoIn> addresses = new ArrayList<>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);

        return addresses;

    }

    @Test
    void getUser() {
        var userFromRepo = new UserEntity();
        userEntity.setAddress(new AddressEntity() {
            {
                setFirstName("A");
                setLastName("B");
            }
        });
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword("123");
        //userEntity.setEnabled(true);
        userEntity.setVerified(true);
        userEntity.setId(UUID.randomUUID());

        when(userRepository.findByEmail(anyString())).thenReturn(userFromRepo);

        var userdto = userService.findByEmail("test@test.com");
        assertNotNull(userdto);
        assertEquals("A", userdto.getAddress().getFirstName());
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

        userEntity = new UserEntity();
        userEntity.setAddress(new AddressEntity() {
            {
                setFirstName("Petr");
                setLastName("Novotny");
            }
        });
        userEntity.setEmail("test@test.com");
        userEntity.setEncryptedPassword(encryptedPassword);
        //userEntity.setEnabled(true);
        userEntity.setVerified(true);
        userEntity.setId(UUID.randomUUID());

        userDtoIn = new UserDtoIn();
        userDtoIn.setAddress(new AddressDtoIn() {
            {
                setFirstName("Petr");
                setLastName("Novotny");
            }
        });
        userDtoIn.setEmail("test@test.com");
    }

    @Test
    final void testCreateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDtoIn userDtoIn = new UserDtoIn();
        List<AddressDtoIn> addressesDto = getAddressesDto();
        //userDtoIn.setAddresses(addressesDto);
        userDtoIn.setAddress(new AddressDtoIn() {
            {
                setFirstName("user_from_test");
                setLastName("user_from_test");
            }
        });
        userDtoIn.setPassword("12345678");
        userDtoIn.setEmail("user_from_test@test.com");

        var storedUserDetails = userService.createUser(userDtoIn);

        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getAddress().getFirstName(), storedUserDetails.getAddress().getFirstName());
        assertEquals(userEntity.getAddress().getLastName(), storedUserDetails.getAddress().getLastName());
        assertNotNull(storedUserDetails.getId());

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