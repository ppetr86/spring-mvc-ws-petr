package com.shopapp.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.Random.class)
class CategoryEntityDaoImplIntegrationTest {

    @Autowired
    private CategoryDaoImpl categoryService;

    @ParameterizedTest
    @CsvSource({
            "clothing",
            "shoes",
            "other",
            "Apparel and accessories",
            "Mobile phones",
            "Notebooks",
            "Consumer Electronic",
            "Books",
            "Movies",
            "Music",
            "Games",
            "Health",
            "Personal care",
            "Beauty",
            "Food and Beverages", "Auto and parts", "Furniture and decor",
    })
    void findByName(String name) {
        Assertions.assertNotNull(categoryService.findByName(name));
    }

    @Test
    void findByName_returnsNullOnNonExisting() {
        Assertions.assertNull(categoryService.findByName("not_existing"));
    }

    @Test
    void existsByName() {
    }
}