package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.BrandEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = true)
class BrandRepositoryTest {

    private final BrandRepository brandRepository;

    private final TestEntityManager testEntityManager;

    private final BrandEntity brand1;
    private final BrandEntity brand2;

    @Autowired
    BrandRepositoryTest(BrandRepository brandRepository, TestEntityManager testEntityManager) {
        this.brandRepository = brandRepository;
        this.testEntityManager = testEntityManager;

        brand1 = new BrandEntity();
        brand1.setName("test1");
        brand1.setLogo("test1");

        brand2 = new BrandEntity();
        brand2.setName("test2");
        brand2.setLogo("test2");
    }

    @ParameterizedTest
    @ValueSource(strings = {"test", "nothing", "apparel and accessories", "consumer electronic"})
    void test_findBrandsByCategoryName(final String brand) {
        var allbrands = brandRepository.findAll();
        var expected = allbrands.stream()
                .filter(each -> each.getCategories()
                        .stream()
                        .anyMatch(cat -> cat.getName().contains(brand)))
                .toList();
        var result = brandRepository.findBrandEntitiesByCategoryName(brand);
        Assertions.assertEquals(expected, result);

    }

}