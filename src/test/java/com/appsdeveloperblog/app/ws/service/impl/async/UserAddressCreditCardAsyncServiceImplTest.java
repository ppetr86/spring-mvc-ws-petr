package com.appsdeveloperblog.app.ws.service.impl.async;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserAddressCreditCardAsyncServiceImplTest {


    @Autowired
    UserAddressCreditCardAsyncServiceImpl userAddressCreditCardAsyncService;

    @Autowired
    UserAddressCreditCardAsyncServiceImplTest(UserAddressCreditCardAsyncServiceImpl userAddressCreditCardAsyncService) {

        this.userAddressCreditCardAsyncService = userAddressCreditCardAsyncService;
    }


    @Test
    void test_retrieveCountsOfUsersAndAddressesAndCards_WithCf() throws ExecutionException, InterruptedException {
        //every of those 3 asked count repository methods is delayed by 1000 ms
        //total time taken is 1019 ms because they run async
        var result = userAddressCreditCardAsyncService.retrieveCountsOfUsersAndAddressesAndCards_WithCf();

        System.out.println("getUserCount " + result.getUserCount());
        System.out.println("getAddressCount " + result.getAddressCount());
        System.out.println("getCreditCardCount " + result.getCreditCardCount());

        assertTrue(true);

    }

    @Test
    void test_retrieveCountsOfUsersAndAddressesAndCards_WithoutCf() {
        //every of those 3 asked count repository methods is delayed by 1000 ms
        //total time taken is more than 3000 ms because they run sync
        var result = userAddressCreditCardAsyncService.retrieveCountsOfUsersAndAddressesAndCards_WithoutCf();
        System.out.println("getUserCount " + result.getUserCount());
        System.out.println("getAddressCount " + result.getAddressCount());
        System.out.println("getCreditCardCount " + result.getCreditCardCount());
        assertTrue(true);
    }

    @Test
    void test_retrieveCountsOfUsersAndAddresses_WithoutCf() {
        var result = userAddressCreditCardAsyncService.retrieveCountsOfUsersAndAddresses_WithoutCf();

        System.out.println("getUserCount " + result.getUserCount());
        System.out.println("getAddressCount " + result.getAddressCount());

        assertTrue(true);
    }

    @Test
    void test_retrieveCountsOfUsersAndAddresses_WithCf() throws ExecutionException, InterruptedException {
        var result = userAddressCreditCardAsyncService.retrieveCountsOfUsersAndAddresses_WithCf();

        System.out.println("getUserCount " + result.getUserCount());
        System.out.println("getAddressCount " + result.getAddressCount());

        assertTrue(true);
    }

    @Test
    void getUserAddressCreditCardEntityDto() throws ExecutionException, InterruptedException {
        var result = userAddressCreditCardAsyncService.getUserAddressCreditCardEntityDto();

        System.out.println("getUserCount " + result.getUsers().size());
        System.out.println("getAddressCount " + result.getAddresses().size());
        System.out.println("getCreditCardCount " + result.getCreditCards().size());

        assertTrue(true);
    }

    @Test
    void getUserAddressCreditCardEntityDtoWithoutCF() {

        var result = userAddressCreditCardAsyncService.getUserAddressCreditCardEntityDtoWithoutCF();

        System.out.println("getUserCount " + result.getUsers().size());
        System.out.println("getAddressCount " + result.getAddresses().size());
        System.out.println("getCreditCardCount " + result.getCreditCards().size());

        assertTrue(true);
    }


}