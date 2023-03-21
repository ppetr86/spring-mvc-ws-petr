package com.shopapp.repository;


import com.shopapp.data.entity.AuthorityEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

//https://www.bezkoder.com/spring-boot-unit-test-jpa-repo-datajpatest/
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true) //commits a transaction to the database true/false
class AuthorityRepositoryTest {

    private final AuthorityRepository authorityRepository;

    //https://zetcode.com/springboot/testentitymanager/
    private final TestEntityManager entityManager;

    static long count;
    static List<AuthorityEntity> all;


    @Autowired
    public AuthorityRepositoryTest(AuthorityRepository authorityRepository, TestEntityManager entityManager) {
        this.authorityRepository = authorityRepository;
        this.entityManager = entityManager;
        count = authorityRepository.count();
        all = authorityRepository.findAll();
    }

    @Test
    void findAll() {
        Assertions.assertThat(all.size()).isEqualTo(count);
    }

    @Test
    void test_findByName() {
        for (var each : all) {
            assertNotNull(authorityRepository.findByName(each.getName()));
        }
    }

    @Test
    void test_findByName_shouldReturnNull() {
        String str = null;
        assertNull(authorityRepository.findByName(str));
    }
}