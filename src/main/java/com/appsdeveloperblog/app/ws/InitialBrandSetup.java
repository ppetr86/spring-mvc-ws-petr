package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.data.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.data.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.service.BrandService;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

@Component
@AllArgsConstructor
public class InitialBrandSetup {

    private BrandService brandService;


    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event InitialBrandSetup...");

        long count = brandService.getCount();
        int targetCount = 20;
        if (count >= targetCount)
            System.out.println("From Application ready event InitialBrandSetup...Will not create brands");
        else {
            createRandomBrands(targetCount);
        }
    }


    @Transactional
    void createRandomBrands(int quantity) {

        /*var addressList = new ArrayList<>();
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
        }*/
    }

}
