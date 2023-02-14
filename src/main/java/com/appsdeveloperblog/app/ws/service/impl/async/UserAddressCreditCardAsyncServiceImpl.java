package com.appsdeveloperblog.app.ws.service.impl.async;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.data.entity.CreditCardEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.data.entitydto.UserAddressCreditCardEntityCounterDto;
import com.appsdeveloperblog.app.ws.data.entitydto.UserAddressCreditCardEntityDto;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.CreditCardService;
import com.appsdeveloperblog.app.ws.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.appsdeveloperblog.app.ws.shared.Utils.startTimer;
import static com.appsdeveloperblog.app.ws.shared.Utils.timeTaken;

@Service
@AllArgsConstructor
public class UserAddressCreditCardAsyncServiceImpl {

    private final UserService userService;

    private final AddressService addressService;

    private final CreditCardService creditCardService;


    public UserAddressCreditCardEntityCounterDto retrieveCountsOfUsersAndAddressesAndCards_WithCf()
            throws ExecutionException, InterruptedException {
        startTimer();
        CompletableFuture<Long> userCountCf = CompletableFuture.supplyAsync(userService::getCountDelayed);
        CompletableFuture<Long> addressCountCf = CompletableFuture.supplyAsync(addressService::getCountDelayed);
        CompletableFuture<Long> cardCountCf = CompletableFuture.supplyAsync(creditCardService::getCountDelayed);

        var future = CompletableFuture.allOf(userCountCf, addressCountCf, cardCountCf)
                .thenApply(combined ->
                        new UserAddressCreditCardEntityCounterDto(userCountCf.join(), addressCountCf.join(), cardCountCf.join()));

        var result = future.get();
        timeTaken();
        return result;
    }

    public UserAddressCreditCardEntityCounterDto retrieveCountsOfUsersAndAddressesAndCards_WithoutCf() {
        startTimer();
        var userCountCf = userService.getCountDelayed();
        var addressCountCf = addressService.getCountDelayed();
        var cardCountCf = creditCardService.getCountDelayed();

        timeTaken();
        return new UserAddressCreditCardEntityCounterDto(userCountCf, addressCountCf, cardCountCf);
    }


    public UserAddressCreditCardEntityCounterDto retrieveCountsOfUsersAndAddresses_WithoutCf() {
        startTimer();
        var userCountCf = userService.getCountDelayed();
        var addressCountCf = addressService.getCountDelayed();

        timeTaken();
        return new UserAddressCreditCardEntityCounterDto(userCountCf, addressCountCf);
    }

    public UserAddressCreditCardEntityCounterDto retrieveCountsOfUsersAndAddresses_WithCf()
            throws ExecutionException, InterruptedException {
        startTimer();
        CompletableFuture<Long> userCountCf = CompletableFuture.supplyAsync(userService::getCountDelayed);
        CompletableFuture<Long> addressCountCf = CompletableFuture.supplyAsync(addressService::getCountDelayed);

        var future = CompletableFuture.allOf(userCountCf, addressCountCf)
                .thenApply(combined ->
                        new UserAddressCreditCardEntityCounterDto(userCountCf.join(), addressCountCf.join()));

        var result = future.get();
        timeTaken();
        return result;
    }


    public UserAddressCreditCardEntityDto getUserAddressCreditCardEntityDto() throws ExecutionException, InterruptedException {
        startTimer();
        CompletableFuture<List<UserEntity>> userCountCf = CompletableFuture.supplyAsync(userService::loadAllDelayed);
        CompletableFuture<List<AddressEntity>> addressCountCf = CompletableFuture.supplyAsync(addressService::loadAllDelayed);
        CompletableFuture<List<CreditCardEntity>> cardCountCf = CompletableFuture.supplyAsync(creditCardService::loadAllDelayed);

        var future = CompletableFuture.allOf(userCountCf, addressCountCf, cardCountCf)
                .thenApply(combined ->
                        new UserAddressCreditCardEntityDto(userCountCf.join(), addressCountCf.join(), cardCountCf.join()));

        var result = future.get();
        timeTaken();
        return result;
    }


    public UserAddressCreditCardEntityDto getUserAddressCreditCardEntityDtoWithoutCF() {
        startTimer();
        var users = userService.loadAllDelayed();
        var addresses = addressService.loadAllDelayed();
        var cards = creditCardService.loadAllDelayed();

        timeTaken();
        return new UserAddressCreditCardEntityDto(users, addresses, cards);
    }

}
