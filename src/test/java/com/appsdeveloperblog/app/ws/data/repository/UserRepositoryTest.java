package com.appsdeveloperblog.app.ws.data.repository;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.repository.AddressRepository;
import com.appsdeveloperblog.app.ws.repository.RoleRepository;
import com.appsdeveloperblog.app.ws.repository.UserRepository;
import com.appsdeveloperblog.app.ws.shared.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {

    static boolean recordsCreated = true;
    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AddressRepository addressRepository;

    @BeforeEach
    void setUp() throws Exception {

        if (!recordsCreated)
            createRecrods();
    }

    @Test
    final void testFindUserByFirstName() {
        String firstName = "Sergey";
        List<UserEntity> users = userRepository.findUserByFirstName(firstName);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getFirstName().equals(firstName));
    }

    @Test
    final void testFindUserByLastName() {
        String lastName = "Kargopolov";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(user.getLastName().equals(lastName));
    }

    @Test
    final void testFindUserEntityByUserId() {
        String userId = "1a2b3c";
        UserEntity userEntity = userRepository.findUserEntityByUserId(userId);

        assertNotNull(userEntity);
        assertTrue(userEntity.getUserId().equals(userId));
    }

    @Test
    final void testFindUserFirstNameAndLastNameByKeyword() {
        String keyword = "e";
        List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        Object[] user = users.get(0);

        assertTrue(user.length == 2);

        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("First name = " + userFirstName);
        System.out.println("Last name = " + userLastName);

    }

    @Test
    final void testFindUsersByKeyword() {
        String keyword = "erg";
        List<UserEntity> users = userRepository.findUsersByKeyword(keyword);
        assertNotNull(users);
        assertTrue(users.size() == 2);

        UserEntity user = users.get(0);
        assertTrue(
                user.getLastName().contains(keyword) ||
                        user.getFirstName().contains(keyword)
        );
    }

    @Test
    final void testGetUserEntityFullNameById() {
        String userId = "1a2b3c";
        List<Object[]> records = userRepository.getUserEntityFullNameById(userId);

        assertNotNull(records);
        assertTrue(records.size() == 1);

        Object[] userDetails = records.get(0);

        String firstName = String.valueOf(userDetails[0]);
        String lastName = String.valueOf(userDetails[1]);

        assertNotNull(firstName);
        assertNotNull(lastName);
    }

    @Test
    final void testGetVerifiedUsers() {
        Pageable pageableRequest = PageRequest.of(1, 1);
        Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
        assertNotNull(page);

        List<UserEntity> userEntities = page.getContent();
        assertNotNull(userEntities);
        assertTrue(userEntities.size() == 1);
    }

    @Test
    final void testUpdateUserEmailVerificationStatus() {
        boolean newIsVerified = true;
        userRepository.updateUserIsVerified(newIsVerified, "1a2b3c");

        UserEntity storedUserDetails = userRepository.findByUserId("1a2b3c");

        boolean storedEmailVerificationStatus = storedUserDetails.isVerified();

        assertTrue(storedEmailVerificationStatus == newIsVerified);

    }

    @Test
    final void testUpdateUserEntityEmailVerificationStatus() {
        boolean newEmailVerificationStatus = true;
        userRepository.updateUserEntityIsVerified(newEmailVerificationStatus, "1a2b3c");

        UserEntity storedUserDetails = userRepository.findByUserId("1a2b3c");

        boolean storedEmailVerificationStatus = storedUserDetails.isVerified();

        assertTrue(storedEmailVerificationStatus == newEmailVerificationStatus);

    }

    @Test
    final void test_findAll() {
        var allusers = userRepository.findAll();
        Assertions.assertEquals(userRepository.count(), allusers.size());
    }

    @Test
    final void test_saveUser() {

        var userRole = roleRepository.findByName("ROLE_USER");
        var address = addressRepository.findAll().stream().filter(Objects::nonNull).findFirst().get();

        var user = new UserEntity();
        user.setUserId(utils.generateUserId(7));

        user.setRoles(Set.of(userRole));
        user.setAddresses(Set.of(address));
        user.setEmail("randomemail@gmail.com");
        user.setEncryptedPassword("not_encrypted_pwd");
        user.setFirstName("test_firstName");
        user.setLastName("test_lastName");

        var persistedUser = userRepository.save(user);
        Assertions.assertNotNull(persistedUser.getCreatedAt());
        Assertions.assertNotNull(persistedUser.getId());

    }

    private void createRecrods() {
        // Prepare User Entity
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("Sergey");
        userEntity.setLastName("Kargopolov");
        userEntity.setUserId("1a2b3c");
        userEntity.setEncryptedPassword("xxx");
        userEntity.setEmail("test@test.com");
        userEntity.setVerified(true);

        // Prepare User Addresses
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressId("ahgyt74hfy");
        addressEntity.setCity("Vancouver");
        addressEntity.setCountry("Canada");
        addressEntity.setPostalCode("ABCCDA");
        addressEntity.setStreetName("123 Street Address");

        userEntity.setAddresses(Set.of(addressEntity));

        userRepository.save(userEntity);


        // Prepare User Entity
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("Sergey");
        userEntity2.setLastName("Kargopolov");
        userEntity2.setUserId("1a2b3cddddd");
        userEntity2.setEncryptedPassword("xxx");
        userEntity2.setEmail("test@test.com");
        userEntity2.setVerified(true);

        // Prepare User Addresses
        AddressEntity addressEntity2 = new AddressEntity();
        addressEntity2.setAddressId("ahgyt74hfywwww");
        addressEntity2.setCity("Vancouver");
        addressEntity2.setCountry("Canada");
        addressEntity2.setPostalCode("ABCCDA");
        addressEntity2.setStreetName("123 Street Address");

        userEntity2.setAddresses(Set.of(addressEntity2));

        userRepository.save(userEntity2);

        recordsCreated = true;
    }


}