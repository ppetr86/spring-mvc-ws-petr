package com.shopapp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true) //commits a transaction to the database true/false
class CreditCardRepositoryTest {

    final String CREDIT_CARD = "12345678900000";
    final int CREDIT_CARD_INT = 123456789;

    //EncryptionService encryptionService;

    CreditCardRepository creditCardRepository;

    JdbcTemplate jdbcTemplate;

    @Autowired
    public CreditCardRepositoryTest(final CreditCardRepository creditCardRepository,
                                    final JdbcTemplate jdbcTemplate) {
        this.creditCardRepository = creditCardRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Test
    void testSaveAndStoreCreditCard() {
        var creditCard = new CreditCardEntity();
        creditCard.setCreditCardNumber(CREDIT_CARD);
        creditCard.setCvv("123");
        creditCard.setExpirationDate("12/2028");

        var savedCC = creditCardRepository.saveAndFlush(creditCard);

        System.out.println("Getting CC from database: " + savedCC.getCreditCardNumber());

        System.out.println("CC At Rest");
        //System.out.println("CC Encrypted: " + encryptionService.encrypt(CREDIT_CARD));

        var creditcards = creditCardRepository.findAll();
        var dbRow = creditcards.stream()
                .filter(each -> each.getId().equals(UUID.fromString("052c405a-6c8a-4e89-bc83-5a638724484f")))
                .findFirst()
                .orElseGet(CreditCardEntity::new);

        String dbCardValue = dbRow.getCreditCardNumber();

        assertThat(savedCC.getCreditCardNumber()).isNotEqualTo(dbCardValue);
        //assertThat(dbCardValue).isEqualTo(encryptionService.encrypt(CREDIT_CARD));

        var fetchedCC = creditCardRepository.findById(savedCC.getId()).get();

        assertThat(savedCC.getCreditCardNumber()).isEqualTo(fetchedCC.getCreditCardNumber());
    }

    @Test
    void test_findAllCreditCardsNumbersAndExpirationsPartialData() {
        var cardsNumbersExpirations = creditCardRepository.findAllCreditCardsNumbersAndExpirationsPartialData();
        var countInRepo = creditCardRepository.count();

        for (Object[] objects : cardsNumbersExpirations) {
            System.out.println("credit card number: " + objects[0] + " validity: " + objects[1]);
        }

        assertThat(cardsNumbersExpirations.size()).isEqualTo(countInRepo);
    }

    @Test
    void test_findAllCreditCardsNumbersAndExpirationsToDto() {
        var createdIds = new HashSet<UUID>();
        String expirationDate = "12/1998";
        for (int i = 0; i < 2; i++) {
            var creditCard = new CreditCardEntity();
            creditCard.setCreditCardNumber(String.valueOf(CREDIT_CARD_INT + i));
            creditCard.setCvv("123");
            creditCard.setExpirationDate(expirationDate);
            var savedCC = creditCardRepository.save(creditCard);
            createdIds.add(savedCC.getId());
        }

        var cardsNumbersExpirations = creditCardRepository.findAllCreditCardsNumbersAndExpirationsToDto(String.valueOf(CREDIT_CARD_INT), expirationDate);
        var allInRepo = creditCardRepository.findAll();
        var countOf121998 = allInRepo.stream()
                .filter(each -> each.getExpirationDate().equalsIgnoreCase(expirationDate) &&
                        each.getCreditCardNumber().equals(String.valueOf(CREDIT_CARD_INT)))
                .count();

        assertThat(cardsNumbersExpirations.size()).isEqualTo(countOf121998);
    }
}