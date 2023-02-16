package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.data.entity.BrandEntity;
import com.appsdeveloperblog.app.ws.data.entity.CategoryEntity;
import com.appsdeveloperblog.app.ws.data.entity.ProductEntity;
import com.appsdeveloperblog.app.ws.service.BrandService;
import com.appsdeveloperblog.app.ws.service.CategoryService;
import com.appsdeveloperblog.app.ws.service.ProductDetailService;
import com.appsdeveloperblog.app.ws.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@DependsOn({"initialCategorySetup", "initialBrandSetup"})
public class InitialProductSetup {


    @Getter
    @Setter
    static class ResponseEnvelope {
        private List<ProductResponse> products;
        private int total;
        private int skip;
        private int limit;
    }

    @Getter
    @Setter
    static class ProductResponse {
        protected int id;
        protected String title;
        protected String description;
        protected int price;
        protected double discountPercentage;
        protected double rating;
        protected int stock;
        protected String brand;
        protected String category;
        protected String thumbnail;
        protected ArrayList<String> images;
    }

    private BrandService brandService;
    private CategoryService categoryService;
    private ProductService productService;
    private ProductDetailService productDetailService;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        long count = productService.getCount();
        int targetCount = 100;
        if (count >= targetCount)
            System.out.println("From Application ready event InitialProductSetup...Will not create...");
        else {
            System.out.println("From Application ready event InitialProductSetup...Creating");
            readProductsFromExternalApi();
        }
    }

    @Transactional
    void readProductsFromExternalApi() {
        var random = new Random();
        var objectMapper = new ObjectMapper();

        ResponseEnvelope responses = null;
        try {
            var httpRequest = HttpRequest.newBuilder(new URI("https://dummyjson.com/products"))
                    .GET()
                    .build();

            var httpClient = HttpClient.newBuilder().build();

            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = response.body();
            responses = objectMapper.readValue(body, ResponseEnvelope.class);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            //nothing..
        }

        if (responses == null)
            return;

        Map<String, CategoryEntity> persistedCategories = categoryService.loadAll()
                .stream()
                .collect(Collectors.toMap(CategoryEntity::getName, Function.identity()));

        responses.products.stream()
                .filter(Objects::nonNull)
                .filter(each -> !productService.existsByName(each.title))
                .forEach(each -> {
                    var product = new ProductEntity();
                    product.setName(each.title);
                    product.setAlias(each.title);
                    product.setShortDescription(each.description);
                    product.setFullDescription(each.description);
                    product.setEnabled(true);
                    product.setInStock(true);
                    product.setCost((float) (each.price * 0.8));
                    product.setPrice(each.price);
                    product.setDiscountPercent((float) each.discountPercentage);
                    product.setReviewCount(random.nextInt(20));
                    product.setAverageRating(random.nextInt(5) + 1);
                    product.setMainImage(each.images.get(0));

                    if (persistedCategories.containsKey(each.category))
                        product.setCategory(persistedCategories.get(each.category));

                    var brand = brandService.findByName(each.brand);
                    if (brand==null) {
                        brand = new BrandEntity();
                        brand.setName(each.brand);
                        brand = brandService.save(brand);
                    }

                    product.setBrand(brand);
                    product.setCustomerCanReview(true);
                    product.setReviewedByCustomer(true);

                    productService.save(product);
                });
    }

}
