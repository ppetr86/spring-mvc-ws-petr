package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.data.Brands;
import com.appsdeveloperblog.app.ws.data.entity.BrandEntity;
import com.appsdeveloperblog.app.ws.service.BrandService;
import com.appsdeveloperblog.app.ws.service.CategoryService;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class InitialBrandSetup {

    private BrandService brandService;

    private CategoryService categoryService;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {

        long count = brandService.getCount();
        int targetCount = Brands.values().length;
        if (count >= targetCount)
            System.out.println("From Application ready event InitialBrandSetup...Will not create");
        else {
            System.out.println("From Application ready event InitialBrandSetup...Creating");
            createBrands();
        }
    }


    @Transactional
    void createBrands() {
        var faker = new Faker();

        for (var brand : Brands.values()) {
            var currentBrand = new BrandEntity();
            currentBrand.setName(brand.getDescription());
            currentBrand.setLogo(faker.internet().image());

            var categories = brand.getCategories().stream()
                    .map(each -> categoryService.findByName(each.getDescription()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            currentBrand.setCategories(categories);
            brandService.save(currentBrand);
        }
    }

}
