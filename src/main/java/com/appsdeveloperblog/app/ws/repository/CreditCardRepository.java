package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.CreditCardEntity;
import com.appsdeveloperblog.app.ws.data.entitydto.CreditCardNumberValidityDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CreditCardRepository extends IdBasedTimeRevisionRepository<CreditCardEntity> {

    @Query("select c.creditCardNumber, c.expirationDate from CreditCardSnapshotEntity c")
    List<Object[]> findAllCreditCardsNumbersAndExpirationsPartialData();


    @Query("select new com.appsdeveloperblog.app.ws.data.entitydto.CreditCardNumberValidityDto(cce.creditCardNumber,cce.expirationDate) " + "from CreditCardSnapshotEntity cce where cce.creditCardNumber like %:creditCardNumber% and cce.expirationDate like %:expirationDate%")
    List<CreditCardNumberValidityDto> findAllCreditCardsNumbersAndExpirationsToDto(String creditCardNumber, String expirationDate);


}
