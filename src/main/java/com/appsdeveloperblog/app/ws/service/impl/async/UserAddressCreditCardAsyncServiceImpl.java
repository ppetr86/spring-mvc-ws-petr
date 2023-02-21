package com.appsdeveloperblog.app.ws.service.impl.async;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.data.entity.CreditCardEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.data.entitydto.UserAddressCreditCardEntityCounterDto;
import com.appsdeveloperblog.app.ws.data.entitydto.UserAddressCreditCardEntityDto;
import com.appsdeveloperblog.app.ws.service.AddressDao;
import com.appsdeveloperblog.app.ws.service.CreditCardDao;
import com.appsdeveloperblog.app.ws.service.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.appsdeveloperblog.app.ws.shared.Utils.startTimer;
import static com.appsdeveloperblog.app.ws.shared.Utils.timeTaken;
import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
@AllArgsConstructor
public class UserAddressCreditCardAsyncServiceImpl {

    private final UserDao userDao;

    private final AddressDao addressDao;

    private final CreditCardDao creditCardDao;

    public UserAddressCreditCardEntityDto getUserAddressCreditCardEntityDto() throws ExecutionException, InterruptedException {
        startTimer();
        CompletableFuture<List<UserEntity>> userCountCf = supplyAsync(userDao::loadAllDelayed);
        CompletableFuture<List<AddressEntity>> addressCountCf = supplyAsync(addressDao::loadAllDelayed);
        CompletableFuture<List<CreditCardEntity>> cardCountCf = supplyAsync(creditCardDao::loadAllDelayed);

        var future = allOf(userCountCf, addressCountCf, cardCountCf)
                .thenApply(combined ->
                        new UserAddressCreditCardEntityDto(userCountCf.join(), addressCountCf.join(), cardCountCf.join()));

        var result = future.get();
        timeTaken();
        return result;
    }

    public UserAddressCreditCardEntityDto getUserAddressCreditCardEntityDtoWithoutCF() {
        startTimer();
        var users = userDao.loadAllDelayed();
        var addresses = addressDao.loadAllDelayed();
        var cards = creditCardDao.loadAllDelayed();

        timeTaken();
        return new UserAddressCreditCardEntityDto(users, addresses, cards);
    }

    public UserAddressCreditCardEntityCounterDto retrieveCountsOfUsersAndAddressesAndCards_WithCf()
            throws ExecutionException, InterruptedException {
        startTimer();
        CompletableFuture<Long> userCountCf = supplyAsync(userDao::getCountDelayed);
        CompletableFuture<Long> addressCountCf = supplyAsync(addressDao::getCountDelayed);
        CompletableFuture<Long> cardCountCf = supplyAsync(creditCardDao::getCountDelayed);

        var future = allOf(userCountCf, addressCountCf, cardCountCf)
                .thenApply(combined ->
                        new UserAddressCreditCardEntityCounterDto(userCountCf.join(), addressCountCf.join(), cardCountCf.join()));

        var result = future.get();
        timeTaken();
        return result;
    }

    public UserAddressCreditCardEntityCounterDto retrieveCountsOfUsersAndAddressesAndCards_WithoutCf() {
        startTimer();
        var userCountCf = userDao.getCountDelayed();
        var addressCountCf = addressDao.getCountDelayed();
        var cardCountCf = creditCardDao.getCountDelayed();

        timeTaken();
        return new UserAddressCreditCardEntityCounterDto(userCountCf, addressCountCf, cardCountCf);
    }

    public UserAddressCreditCardEntityCounterDto retrieveCountsOfUsersAndAddresses_WithCf()
            throws ExecutionException, InterruptedException {
        startTimer();
        CompletableFuture<Long> userCountCf = supplyAsync(userDao::getCountDelayed);
        CompletableFuture<Long> addressCountCf = supplyAsync(addressDao::getCountDelayed);

        var future = allOf(userCountCf, addressCountCf)
                .thenApply(combined ->
                        new UserAddressCreditCardEntityCounterDto(userCountCf.join(), addressCountCf.join()));

        var result = future.get();
        timeTaken();
        return result;
    }

    public UserAddressCreditCardEntityCounterDto retrieveCountsOfUsersAndAddresses_WithoutCf() {
        startTimer();
        var userCountCf = userDao.getCountDelayed();
        var addressCountCf = addressDao.getCountDelayed();

        timeTaken();
        return new UserAddressCreditCardEntityCounterDto(userCountCf, addressCountCf);
    }

}
