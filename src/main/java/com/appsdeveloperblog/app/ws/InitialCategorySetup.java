package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.data.Categories;
import com.appsdeveloperblog.app.ws.data.entity.CategoryEntity;
import com.appsdeveloperblog.app.ws.service.CategoryService;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class InitialCategorySetup {

    private CategoryService categoryService;


    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event InitialCategorySetup...");

        long count = categoryService.getCount();
        int targetCount = Categories.values().length;
        if (count >= targetCount)
            System.out.println("From Application ready event InitialCategorySetup...Will not create brands");
        else {
            createCategories();
        }
    }


    void createCategories() {

        var mapChildrenToParent = new HashMap<Categories, Categories>();
        Faker faker = new Faker();
        for (var each : Categories.values()) {
            if (!each.getChildren().isEmpty()) {
                each.getChildren().forEach(eachChild -> mapChildrenToParent.put(eachChild, each));
            }
        }

        for (Categories categories : Categories.values()) {
            var category = new CategoryEntity();
            category.setName(categories.getDescription());
            category.setAlias(categories.getDescription());
            category.setImage(faker.internet().image());
            category.setEnabled(true);
            categoryService.save(category);
        }

        //saving parent / children relationships

        for (var each : Categories.values()) {
            var category = categoryService.findByName(each.getDescription());

            //each is parent and will set child on it
            if (each.getChildren().isEmpty() && mapChildrenToParent.containsKey(each)) {
                var parentCategoryName = mapChildrenToParent.get(each).getDescription();
                var parentCategory = categoryService.findByName(parentCategoryName);
                category.setParent(parentCategory);
            } else {
                //each is child and will set children on it
                category.setHasChildren(true);
                var childEntities = each.getChildren().stream()
                        .map(child -> categoryService.findByName(child.getDescription()))
                        .collect(Collectors.toSet());
                category.setChildren(childEntities);
            }
            categoryService.save(category);
        }
    }

}
