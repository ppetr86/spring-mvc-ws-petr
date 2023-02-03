package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.io.entity.address.AddressEntity;
import com.appsdeveloperblog.app.ws.io.repository.AddressRepository;
import com.appsdeveloperblog.app.ws.io.repository.AuthorityRepository;
import com.appsdeveloperblog.app.ws.io.repository.RoleRepository;
import com.appsdeveloperblog.app.ws.io.repository.UserRepository;
import com.appsdeveloperblog.app.ws.shared.Utils;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class InitialAddressSetup {

    AuthorityRepository authorityRepository;

    RoleRepository roleRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    Utils utils;

    UserRepository userRepository;

    AddressRepository addressRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event InitialAddressSetup...");

        if (addressRepository.count() >= 50)
            System.out.println("From Application ready event InitialAddressSetup...Will not create addresses");
        else {
            var faker = new Faker();
            int size = 50;
            var allAddressses = addressRepository.findAll();

            for (int i = 0; i < size; i++) {
                var zipCode = faker.address().zipCode();
                while (zipCode.length() > 7) {
                    zipCode = faker.address().zipCode();
                }

                var country = faker.address().country();
                while (country.length() > 15) {
                    country = faker.address().country();
                }

                var streetName = faker.address().streetName();
                while (streetName.length() > 100) {
                    streetName = faker.address().streetName();
                }

                var city = faker.address().city();
                while (city.length() > 15) {
                    city = faker.address().city();
                }

                var addressEntity = new AddressEntity(utils.generateAddressId(5),
                        city,
                        country, streetName,
                        zipCode.substring(0, Math.min(zipCode.length(), 7)));

                if (!allAddressses.contains(addressEntity)) {
                    addressRepository.save(addressEntity);
                }
            }
        }
    }

}
