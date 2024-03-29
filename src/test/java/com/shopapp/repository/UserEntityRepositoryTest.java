package com.shopapp.repository;

import com.shopapp.data.entity.UserEntity;
import com.shopapp.shared.Roles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserEntityRepositoryTest {

    static boolean recordsCreated = true;

    UserRepository userRepository;

    RoleRepository roleRepository;

    AddressRepository addressRepository;

    UserEntity testedUser;

    List<UserEntity> allusers;

    @Autowired
    public UserEntityRepositoryTest(UserRepository userRepository,
                                    RoleRepository roleRepository,
                                    AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.roleRepository = roleRepository;
        if (!recordsCreated)
            createRecords();
        testedUser = userRepository.findByEmail("Sergey@test.com");

        allusers = userRepository.findAll();
    }


    @Test
    final void testFindUserByFirstName() {
        String firstName = "Sergey";
        List<UserEntity> users = userRepository.findUserByFirstName(firstName);
        assertNotNull(users);
        assertEquals(2, users.size());

        UserEntity user = users.get(0);
        assertEquals(user.getFirstName(), firstName);
    }

    @Test
    final void testFindUserByLastName() {
        String lastName = "Kargopolov";
        List<UserEntity> users = userRepository.findUserByLastName(lastName);
        assertNotNull(users);
        assertEquals(2, users.size());

        UserEntity user = users.get(0);
        assertEquals(user.getLastName(), lastName);
    }

    @Test
    final void testFindUserFirstNameAndLastNameByKeyword() {
        String keyword = "Sergey";
        List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
        var expectedCount = allusers.stream()
                .filter(each -> each.getFirstName().equalsIgnoreCase(keyword) || each.getLastName().equalsIgnoreCase(keyword))
                .count();
        assertNotNull(users);
        assertEquals(expectedCount, users.size());

        Object[] user = users.get(0);

        assertEquals(2, user.length);

        String userFirstName = String.valueOf(user[0]);
        String userLastName = String.valueOf(user[1]);

        assertNotNull(userFirstName);
        assertNotNull(userLastName);

        System.out.println("First name = " + userFirstName);
        System.out.println("Last name = " + userLastName);

    }

    @Test
    final void testFindUsersByKeyword() {
        String keyword = "Petr";
        List<UserEntity> users = userRepository.findUsersByKeyword(keyword);
        var countOfPetrs = userRepository.findAll().stream()
                .filter(each -> each.getFirstName().equals(keyword) || each.getLastName().equals(keyword))
                .count();
        assertNotNull(users);
        assertEquals(countOfPetrs, users.size());

        UserEntity user = users.get(0);
        assertTrue(
                user.getLastName().contains(keyword) ||
                        user.getFirstName().contains(keyword)
        );
    }

    @Test
    final void testGetUserEntityFullNameById() {
        List<Object[]> records = userRepository.getUserEntityFullNameById(testedUser.getId());

        assertNotNull(records);
        assertEquals(1, records.size());

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
        assertEquals(1, userEntities.size());
    }

    @Test
    final void testUpdateUserEmailVerificationStatus() {
        boolean newIsVerified = true;
        userRepository.updateUserIsVerified(newIsVerified, testedUser.getId());

        UserEntity storedUserDetails = userRepository.findById(testedUser.getId()).orElse(null);
        if (storedUserDetails != null) {
            boolean storedEmailVerificationStatus = storedUserDetails.isVerified();

            assertEquals(storedEmailVerificationStatus, newIsVerified);
        }

    }

    @Test
    final void testUpdateUserEntityEmailVerificationStatus() {
        boolean newEmailVerificationStatus = true;
        userRepository.updateUserEntityIsVerified(newEmailVerificationStatus, testedUser.getId());

        UserEntity storedUserDetails = userRepository.findById(testedUser.getId()).orElse(null);

        if (storedUserDetails != null) {
            boolean storedEmailVerificationStatus = storedUserDetails.isVerified();

            assertEquals(storedEmailVerificationStatus, newEmailVerificationStatus);
        }
    }

    @Test
    final void test_findAll() {
        long count = userRepository.count();
        Assertions.assertTrue(count > 0);
        Assertions.assertEquals(count, allusers.size());
    }

    @Test
    final void test_saveUser() {

        var userRole = roleRepository.findByName(Roles.ROLE_USER);

        var user = new UserEntity();

        user.setRoles(Set.of(userRole));
        user.setEmail("randomemail@gmail.com");
        user.setEncryptedPassword("not_encrypted_pwd");
        user.setFirstName("test_firstName");
        user.setLastName("test_lastName");

        var persistedUser = userRepository.save(user);
        Assertions.assertNotNull(persistedUser.getCreatedAt());
        Assertions.assertNotNull(persistedUser.getId());

    }

    private void createRecords() {
        // Prepare User Entity
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("petr");
        userEntity.setLastName("petr");
        userEntity.setEncryptedPassword("xxx");
        userEntity.setEmail("petr@test.com");
        userEntity.setVerified(true);

        userRepository.save(userEntity);

        // Prepare User Entity
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setFirstName("ivana");
        userEntity2.setLastName("ivana");
        userEntity2.setEncryptedPassword("xxx");
        userEntity2.setEmail("ivana@test.com");
        userEntity2.setVerified(true);

        userRepository.save(userEntity2);

        recordsCreated = true;
    }


}