package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.io.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.io.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.io.entity.UserEntity;
import com.appsdeveloperblog.app.ws.io.entity.address.AddressEntity;
import com.appsdeveloperblog.app.ws.io.repository.AddressRepository;
import com.appsdeveloperblog.app.ws.io.repository.AuthorityRepository;
import com.appsdeveloperblog.app.ws.io.repository.RoleRepository;
import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.shared.Roles;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@AllArgsConstructor
public class InitialUsersSetup {

    AuthorityRepository authorityRepository;

    RoleRepository roleRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    Utils utils;

    UserRepository userRepository;

    AddressRepository addressRepository;

    private final int userIdDefaultLength = 10;


    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event InitialUsersSetup...");

        if (userRepository.count() >= 50)
            System.out.println("From Application ready event InitialUsersSetup...Will not create users");
        else {
            var readAuthority = createAuthority("READ_AUTHORITY");
            var writeAuthority = createAuthority("WRITE_AUTHORITY");
            var deleteAuthority = createAuthority("DELETE_AUTHORITY");

            var roleAdmin = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

            if (roleAdmin == null) return;

            var adminUser = new UserEntity();
            setUserProperties(adminUser, "Petr", "Novotny", "test@test.com", roleAdmin);

            UserEntity storedUserDetails = userRepository.findByEmail("test@test.com");
            if (storedUserDetails == null) {
                userRepository.save(adminUser);
            }

            var roleUser = createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
            var userUser = new UserEntity();
            setUserProperties(userUser, "Ivana", "Michalova", "ivana@test.com", roleUser);

            var storedUserUserDetails = userRepository.findByEmail("ivana@test.com");
            if (storedUserUserDetails == null) {
                userRepository.save(userUser);
            }

            createRandomUsers(50, roleUser);
        }


    }

    private void setUserProperties(UserEntity adminUser, String Petr, String Novotny, String email, RoleEntity roleAdmin) {
        adminUser.setFirstName(Petr);
        adminUser.setLastName(Novotny);
        adminUser.setEmail(email);
        adminUser.setEmailVerificationStatus(true);
        adminUser.setUserId(utils.generateUserId(userIdDefaultLength));
        adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("1234"));
        adminUser.setRoles(List.of(roleAdmin));
        adminUser.setAddresses(getAddresses(adminUser));
    }

    @Transactional
    void createRandomUsers(int quantity, RoleEntity role) {

        var addressList = new ArrayList<>(addressRepository.findAll());
        var randomObj = new Random();
        var resultSet = new HashSet<UserEntity>();

        var faker = new Faker();
        for (int i = 0; i < quantity; i++) {
            var randomUser = new UserEntity();
            randomUser.setUserId(utils.generateUserId(userIdDefaultLength));
            randomUser.setFirstName(faker.name().firstName());
            randomUser.setLastName(faker.name().lastName());
            randomUser.setEmail(randomUser.getFirstName() + randomUser.getLastName() + "@test.com");
            randomUser.setEncryptedPassword(bCryptPasswordEncoder.encode("1234"));
            randomUser.setRoles(List.of(role));
            randomUser.setAddresses(List.of(addressList.get(randomObj.nextInt(addressList.size()))));
            if (!userRepository.existsByEmail(randomUser.getEmail())) {
                resultSet.add(randomUser);
            }
        }
        userRepository.saveAll(resultSet);
    }

    private List<AddressEntity> getAddresses(UserEntity userUser) {
        return List.of(new AddressEntity(utils.generateAddressId(5),
                "Praha", "Czech republic",
                "Kocianova", "155 00"));
    }

    @Transactional
    AuthorityEntity createAuthority(String name) {

        AuthorityEntity authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {

        RoleEntity role = roleRepository.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }

}
