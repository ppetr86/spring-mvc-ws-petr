package com.shopapp.repository;

import com.shopapp.data.entity.CreditCardEntity;
import com.shopapp.data.entitydto.CreditCardNumberValidityDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardRepository extends IdBasedTimeRevisionRepository<CreditCardEntity> {

    @Query("select c.creditCardNumber, c.expirationDate from CreditCardSnapshotEntity c")
    List<Object[]> findAllCreditCardsNumbersAndExpirationsPartialData();

    @Query("select new com.shopapp.data.entitydto.CreditCardNumberValidityDto(cce.creditCardNumber,cce.expirationDate) " +
            "from CreditCardEntity cce where cce.creditCardNumber like %:creditCardNumber% and " +
            "cce.expirationDate like %:expirationDate%")
    List<CreditCardNumberValidityDto> findAllCreditCardsNumbersAndExpirationsToDto(String creditCardNumber, String expirationDate);

}
