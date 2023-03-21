package com.shopapp.repository;

import com.shopapp.data.entity.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.UUID;
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
    void findAllByUser() throws ExecutionException, InterruptedException, TimeoutException {
        //var randomUser = userRepository.findByEmail("test@test.com");
        var randomUser = new UserEntity();
        String uuidString = "86c93694-5161-435f-a28d-f1b8d8cff312";
        randomUser.setId(UUID.fromString(uuidString));

        var repoUser = userRepository.findById(UUID.fromString(uuidString));
        var futureListAddress = addressRepository.findAllByUsersContains(randomUser);

        var amountOfAddresses = repoUser.map(user -> user.getAddresses().size()).orElseGet(() -> 0);

        Assertions.assertThat(amountOfAddresses)
                .isEqualTo(futureListAddress.get(5, TimeUnit.SECONDS).size());
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