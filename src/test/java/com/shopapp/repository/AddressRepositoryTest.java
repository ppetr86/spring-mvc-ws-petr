package com.shopapp.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//https://www.bezkoder.com/spring-boot-unit-test-jpa-repo-datajpatest/
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true) //commits a transaction to the database true/false
class AddressRepositoryTest {

    private final AddressRepository addressRepository;

    //https://zetcode.com/springboot/testentitymanager/
    private final TestEntityManager entityManager;

    private final UserRepository userRepository;

    @Autowired
    public AddressRepositoryTest(AddressRepository addressRepository,
                                 TestEntityManager entityManager,
                                 UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.entityManager = entityManager;
        this.userRepository = userRepository;
    }

    @Test
    void findAll() {
        var count = addressRepository.count();
        var all = addressRepository.findAll();
        Assertions.assertThat(all.size()).isEqualTo(count);
    }

    @Test
    void findByAddressId() {
    }

    @Test
    void queryByCityEqualsIgnoreCase() throws ExecutionException, InterruptedException, TimeoutException {
        var addressFuture = addressRepository.findFirstByCityEqualsIgnoreCase("praha");
        Assertions.assertThat(addressFuture.get(5, TimeUnit.SECONDS).getCity().toLowerCase())
                .isEqualTo("praha");
    }

    @Test
    void testFindAll() {
        var pageSize = 10;
        var all = addressRepository.findAll(Pageable.ofSize(pageSize));
        Assertions.assertThat(all.get().count()).isEqualTo(pageSize);
    }
}