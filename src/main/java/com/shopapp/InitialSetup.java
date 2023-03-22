package com.shopapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.shopapp.data.Brands;
import com.shopapp.data.Categories;
import com.shopapp.data.entity.*;
import com.shopapp.data.entity.product.ProductEntity;
import com.shopapp.service.*;
import com.shopapp.shared.Authority;
import com.shopapp.shared.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class InitialSetup {

    private final String password = "1234";
    private final String emailPostFix = "@test.com";
    private AuthorityDao authorityDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private BrandDao brandDao;
    private CategoryDao categoryDao;
    private ProductDao productDao;
    private RoleDao roleDao;
    private UserDao userDao;
    private CreditCardDao creditCardDao;

    private AuthorityEntity createAuthority(Authority value) {
        var entity = authorityDao.findByAuthority(value);
        if (entity != null)
            return entity;

        entity = new AuthorityEntity();
        entity.setName(value);
        entity = authorityDao.save(entity);
        return entity;
    }

    @Transactional
    void createBrands() {
        var faker = new Faker();

        for (var brand : Brands.values()) {
            var currentBrand = new BrandEntity();
            currentBrand.setName(brand.getDescription());
            currentBrand.setLogo(faker.internet().image());

            var categories = brand.getCategories().stream()
                    .map(each -> categoryDao.findByName(each.getDescription()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            categories.forEach(currentBrand::addCategory);
            brandDao.save(currentBrand);
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

        //saving categories without child parent relationship
        for (Categories categories : Categories.values()) {
            var category = new CategoryEntity();
            category.setName(categories.getDescription());
            category.setAlias(categories.getDescription());
            category.setImage(faker.internet().image());
            category.setEnabled(true);
            categoryDao.save(category);
        }

        //saving parent / children relationships
        for (var each : Categories.values()) {
            var category = categoryDao.findByName(each.getDescription());

            //each is parent and will set child on it
            if (each.getChildren().isEmpty() && mapChildrenToParent.containsKey(each)) {
                var parentCategoryName = mapChildrenToParent.get(each).getDescription();
                var parentCategory = categoryDao.findByName(parentCategoryName);
                category.setParent(parentCategory);
            } else {
                //each is child, add child to category
                category.setHasChildren(true);
                each.getChildren().stream()
                        .map(child -> categoryDao.findByName(child.getDescription()))
                        .forEach(category::addChildCategory);
            }
            categoryDao.save(category);
        }
    }

    private void createCreditCards(int targetCountOfCreditCards) {

    }

    private void createProductsFromExternalApi() {
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

        Map<String, CategoryEntity> persistedCategories = categoryDao.loadAll()
                .stream()
                .collect(Collectors.toMap(CategoryEntity::getName, Function.identity()));

        responses.products.stream()
                .filter(Objects::nonNull)
                .filter(each -> !productDao.existsByName(each.title))
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

                    var brand = brandDao.findByName(each.brand);
                    if (brand == null) {
                        brand = new BrandEntity();
                        brand.setName(each.brand);
                        brand.setLogo("placeholder");
                        brand = brandDao.save(brand);
                    }

                    product.setBrand(brand);
                    product.setCustomerCanReview(true);
                    product.setReviewedByCustomer(true);

                    productDao.save(product);
                });
    }

    private RoleEntity createRole(Roles name, Set<AuthorityEntity> authorities) {
        var role = roleDao.findByName(name);
        if (role == null)
            role = new RoleEntity();

        role.setName(name);
        for (AuthorityEntity each : authorities) {
            role.addAuthority(each);
        }

        role = roleDao.save(role);
        return role;
    }

    private void createRoles() {
        var readAuthority = authorityDao.findByAuthority(Authority.READ_AUTHORITY);
        var writeAuthority = authorityDao.findByAuthority(Authority.WRITE_AUTHORITY);
        var deleteAuthority = authorityDao.findByAuthority(Authority.DELETE_AUTHORITY);

        createRole(Roles.ROLE_ADMIN, Set.of(readAuthority, writeAuthority, deleteAuthority));
        createRole(Roles.ROLE_USER, Set.of(readAuthority, writeAuthority));
        createRole(Roles.ROLE_CUSTOMER, Set.of(readAuthority));
    }

    private void createUsers() {

        var faker = new Faker();
        var userSet = new HashSet<UserEntity>();

        var roleAdmin = roleDao.findByName(Roles.ROLE_ADMIN);
        var petr = new UserEntity();
        setUserProperties(petr, "Petr", "Novotny", "petr" + emailPostFix, roleAdmin, faker);
        userSet.add(petr);

        var roleUser = roleDao.findByName(Roles.ROLE_USER);
        var ivana = new UserEntity();
        setUserProperties(ivana, "Ivana", "Michalova", "ivana" + emailPostFix, roleUser, faker);

        userSet.add(ivana);

        var test = new UserEntity();
        setUserProperties(test, "test_name", "test_name", "test_email" + emailPostFix, roleUser, faker);
        userSet.add(test);

        var roleCustomer = roleDao.findByName(Roles.ROLE_CUSTOMER);
        var customer = new UserEntity();
        setUserProperties(customer, "customer", "customer", "customer" + emailPostFix, roleCustomer, faker);
        userSet.add(customer);

        userDao.saveAll(userSet);

    }

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) throws ExecutionException, InterruptedException {

        if (authorityDao.getCount() < Authority.values().length) {
            //create authorities which are authorization
            for (var each : Authority.values()) {
                createAuthority(each);
                createAuthority(each);
                createAuthority(each);
            }
        }

        if (roleDao.getCount() < Roles.values().length) {
            //create roles which depend on authorities
            //users depend on roles
            createRoles();
        }

        int targetCountOfCategories = Categories.values().length;
        if (categoryDao.getCount() < targetCountOfCategories) {
            System.out.println("From Application ready event categories...Creating");
            createCategories();
        }

        //brands depend on categories
        int targetCountOfBrands = Brands.values().length;
        if (brandDao.getCount() < targetCountOfBrands) {
            System.out.println("From Application ready event brands...Creating");
            createBrands();
        }

        //products depend on brands and categories
        int targetCountOfProducts = 30;
        if (productDao.getCount() < targetCountOfProducts) {
            System.out.println("From Application ready event products...Creating");
            CompletableFuture.runAsync(this::createProductsFromExternalApi);
        }

        //create users
        int targetCountOfUsers = 4;
        CompletableFuture<Void> cfUsers = CompletableFuture.completedFuture(null);
        if (userDao.getCount() < targetCountOfUsers) {
            System.out.println("From Application ready event users...Creating");
            cfUsers = CompletableFuture.runAsync(this::createUsers);
        }
    }

    private void setUserProperties(UserEntity user, String firstName, String lastName, String email, RoleEntity role, Faker faker) {
        user.setId(UUID.randomUUID());

        var address = new AddressEntity();
        address.setFirstName(firstName);
        address.setLastName(lastName);
        address.setUser(user);
        address.setDefaultForShipping(true);

        String phoneNumber = faker.phoneNumber().phoneNumber();
        address.setPhoneNumber(phoneNumber.substring(0, Math.min(phoneNumber.length(), 15)));

        address.setAddressLine1(faker.address().streetAddress());
        address.setCity(faker.address().city());
        address.setCountry(faker.address().country());
        address.setPostalCode(faker.address().zipCode());

        user.setAddress(address);
        user.setEmail(email);
        user.setVerified(true);
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(password));

        var setOfCards = new HashSet<CreditCardEntity>(2);
        var cc = 12345678900000L;
        var random = new Random();
        for (int i = 0; i < 2; i++) {
            var current = new CreditCardEntity();
            var month = (int) ((Math.random() * (12)));
            current.setCreditCardNumber(String.valueOf(cc++));
            current.setExpirationDate(month + "/" + "23");
            current.setCvv(String.valueOf(random.nextInt(900) + 100));
            setOfCards.add(current);
        }
        user.setCreditCards(setOfCards);

        user.addRole(role);
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

    @Getter
    @Setter
    static class ResponseEnvelope {
        private List<ProductResponse> products;
        private int total;
        private int skip;
        private int limit;
    }

}
