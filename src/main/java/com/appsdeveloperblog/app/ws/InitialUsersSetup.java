package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.data.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.data.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.AuthorityService;
import com.appsdeveloperblog.app.ws.service.RoleService;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.shared.Roles;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@AllArgsConstructor
@DependsOn("initialAddressSetup")
public class InitialUsersSetup {

    AuthorityService authorityService;

    RoleService<RoleEntity> roleService;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    Utils utils;

    UserService userService;

    AddressService addressService;

    private final int userIdDefaultLength = 10;


    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event InitialUsersSetup...");

        long count = userService.getCount();
        if (count >= 50)
            System.out.println("From Application ready event InitialUsersSetup...Will not create users");
        else {
            var readAuthority = createAuthority("READ_AUTHORITY");
            var writeAuthority = createAuthority("WRITE_AUTHORITY");
            var deleteAuthority = createAuthority("DELETE_AUTHORITY");

            var roleAdmin = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

            if (roleAdmin == null) return;

            var adminUser = new UserEntity();
            setUserProperties(adminUser, "Petr", "Novotny", "test@test.com", roleAdmin);

            UserEntity storedUserDetails = userService.findByEmail("test@test.com");
            if (storedUserDetails == null) {
                userService.save(adminUser);
            }

            var roleUser = createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
            var userUser = new UserEntity();
            setUserProperties(userUser, "Ivana", "Michalova", "ivana@test.com", roleUser);

            var storedUserUserDetails = userService.findByEmail("ivana@test.com");
            if (storedUserUserDetails == null) {
                userService.save(userUser);
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
        adminUser.setRoles(Set.of(roleAdmin));
        adminUser.setAddresses(getAddresses(adminUser));
    }

    @Transactional
    void createRandomUsers(int quantity, RoleEntity role) {

        var addressList = new ArrayList<>(addressService.loadAll());
        var randomObj = new Random();
        var faker = new Faker();

        for (int i = 0; i < quantity; i++) {
            var currentUser = new UserEntity();
            currentUser.setUserId(utils.generateUserId(userIdDefaultLength));
            currentUser.setFirstName(faker.name().firstName());
            currentUser.setLastName(faker.name().lastName());
            currentUser.setEmail(currentUser.getFirstName() + currentUser.getLastName() + "@test.com");
            currentUser.setEncryptedPassword(bCryptPasswordEncoder.encode("1234"));
            currentUser.setRoles(Set.of(role));
            var randomAddress = addressList.get(randomObj.nextInt(addressList.size()));
            currentUser.setAddresses(Set.of(randomAddress));
            if (!userService.existsByEmail(currentUser.getEmail())) {
                userService.save(currentUser);
                //save the user first, setUser on address later
                randomAddress.setUser(currentUser);
            }
        }
    }

    private Set<AddressEntity> getAddresses(UserEntity userUser) {
        AddressEntity addressEntity = new AddressEntity(utils.generateId(5),
                "Praha", "Czech republic",
                "Kocianova", "155 00");
        addressEntity.setUser(userUser);
        return Set.of(addressEntity);
    }

    @Transactional
    AuthorityEntity createAuthority(String name) {

        AuthorityEntity authority = authorityService.findByName(name);
        if (authority == null) {
            authority = new AuthorityEntity(name);
            authorityService.save(authority);
        }
        return authority;
    }

    @Transactional
    RoleEntity createRole(String name, Collection<AuthorityEntity> authorities) {

        RoleEntity role = roleService.findByName(name);
        if (role == null) {
            role = new RoleEntity(name);
            role.setAuthorities(authorities);
            roleService.save(role);
        }
        return role;
    }

}
