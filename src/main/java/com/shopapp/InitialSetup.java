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

    private AuthorityDao authorityDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AddressDao addressDao;
    private BrandDao brandDao;
    private CategoryDao categoryDao;
    private ProductDao productDao;
    private ProductDetailDao productDetailDao;
    private RoleDao roleDao;
    private UserDao userDao;
    private CreditCardDao creditCardDao;
    private CustomerDao customerDao;

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

        //users depend on roles, authorities
        int targetCountOfUsers = 3;
        if (userDao.getCount() >= targetCountOfUsers)
            System.out.println("From Application ready event users...Will not create");
        else {
            System.out.println("From Application ready event users...Creating");
            CompletableFuture.runAsync(this::createUsers);
        }

        int targetCountOfCategories = Categories.values().length;
        if (categoryDao.getCount() >= targetCountOfCategories)
            System.out.println("From Application ready event categories...Will not create");
        else {
            System.out.println("From Application ready event categories...Creating");
            createCategories();
        }

        //brands depend on categories
        int targetCountOfBrands = Brands.values().length;
        if (brandDao.getCount() >= targetCountOfBrands)
            System.out.println("From Application ready event brands...Will not create");
        else {
            System.out.println("From Application ready event brands...Creating");
            createBrands();
        }

        //products depend on brands and categories
        int targetCountOfProducts = 100;
        if (productDao.getCount() >= targetCountOfProducts)
            System.out.println("From Application ready event products...Will not create...");
        else {
            System.out.println("From Application ready event products...Creating");
            CompletableFuture.runAsync(this::createProductsFromExternalApi);
        }

        //create customers
        int targetCountOfCustomers = 10;
        CompletableFuture<Void> cfCustomers = CompletableFuture.completedFuture(null);
        if (customerDao.getCount() >= targetCountOfCustomers)
            System.out.println("From Application ready event customers...Will not create");
        else {
            System.out.println("From Application ready event customers...Creating");
            cfCustomers = CompletableFuture.runAsync(() -> createCustomers(targetCountOfCustomers));
        }

        int targetCountOfCreditCards = targetCountOfCustomers * 2;
        CompletableFuture<Void> cfCreditCards = CompletableFuture.completedFuture(null);;
        if (creditCardDao.getCount() >= targetCountOfCreditCards)
            System.out.println("From Application ready event credit cards...Will not create...");
        else {
            System.out.println("From Application ready event credit cards...Creating");
            cfCreditCards = CompletableFuture.runAsync(() -> createCreditCards(targetCountOfCreditCards));
        }

        //when cfUsers and cfCards ready... set cards on users
        CompletableFuture.allOf(cfCreditCards, cfCustomers).get();


        CompletableFuture.runAsync(this::setCreditCardsOnUsers);

    }

    private Set<CustomerEntity> createCustomers( int targetCountOfCustomers) {

        Set<CustomerEntity> customerEntities = new HashSet<>(targetCountOfCustomers);
        var faker = new Faker();

        for (int i = 0; i < targetCountOfCustomers; i++) {
            var customer = new CustomerEntity();
            customer.setId(UUID.randomUUID());

            var address = new AddressEntity();
            address.setFirstName(faker.name().firstName());
            address.setLastName(faker.name().lastName());
            address.setCustomer(customer);
            address.setDefaultForShipping(true);

            String phoneNumber = faker.phoneNumber().phoneNumber();
            address.setPhoneNumber(phoneNumber.substring(0,Math.min(phoneNumber.length(),15)));

            address.setAddressLine1(faker.address().streetAddress());
            address.setCity(faker.address().city());
            address.setCountry(faker.address().country());
            address.setPostalCode(faker.address().zipCode());

            customer.setAddress(address);
            customer.setEmail(customer.getAddress().getFirstName() + customer.getAddress().getLastName() + "@test.com");
            customer.setEncryptedPassword(bCryptPasswordEncoder.encode("1234"));
            customer.setVerified(true);
            customer.setAuthenticationType(AuthenticationType.DATABASE);

            customerEntities.add(customer);
        }

        return new HashSet<>(customerDao.saveAll(customerEntities));
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

    @Transactional
    void createUser(RoleEntity role, ArrayList<AddressEntity> addressList, Random randomObj, Faker faker) {
        var currentUser = new UserEntity();
        currentUser.setFirstName(faker.name().firstName());
        currentUser.setLastName(faker.name().lastName());
        currentUser.setEmail(currentUser.getFirstName() + currentUser.getLastName() + "@test.com");
        currentUser.setEncryptedPassword(bCryptPasswordEncoder.encode("1234"));

        currentUser.addRole(role);

        var randomAddress = addressList.get(randomObj.nextInt(addressList.size()));

        if (!userDao.existsByEmail(currentUser.getEmail())) {
            userDao.save(currentUser);
        }
    }

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
    void setCreditCardsOnUsers() {
        var cards = creditCardDao.loadAll();
        var customers = customerDao.loadAll();
        var cardCounter = 0;
        for (CustomerEntity each : customers) {
            each.addCreditCard(cards.get(cardCounter));
            each.addCreditCard(cards.get(cardCounter + 1));
            cardCounter += 2;
            customerDao.save(each);
        }
    }

    private void createCreditCards(int targetCountOfCreditCards) {
        var setOfCards = new HashSet<CreditCardEntity>(targetCountOfCreditCards);
        var cc = 12345678900000L;
        var random = new Random();
        for (int i = 0; i < targetCountOfCreditCards; i++) {
            var current = new CreditCardEntity();
            var month = i % 12 < 10 ? "0" + i % 12 : "" + i;
            current.setCreditCardNumber(String.valueOf(cc++));
            current.setExpirationDate(month + "/" + "23");
            current.setCvv(String.valueOf(random.nextInt(900) + 100));
            setOfCards.add(current);
        }
        creditCardDao.saveAll(setOfCards);
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

        var roleAdmin = roleDao.findByName(Roles.ROLE_ADMIN);
        var petr = new UserEntity();

        setUserProperties(petr, "Petr", "Novotny", "petr@test.com", roleAdmin);
        if (!userDao.existsByEmail("petr@test.com")) {
            userDao.save(petr);
        }

        var roleUser = roleDao.findByName(Roles.ROLE_USER);
        var ivana = new UserEntity();
        setUserProperties(ivana, "Ivana", "Michalova", "ivana@test.com", roleUser);
        if (!userDao.existsByEmail("ivana@test.com")) {
            userDao.save(ivana);
        }

        var test = new UserEntity();
        setUserProperties(test, "test_name", "test_name", "test_email@test.com", roleUser);
        if (!userDao.existsByEmail("test_email@test.com")) {
            userDao.save(test);
        }
    }

    private void setUserProperties(UserEntity user, String firstName, String lastName, String email, RoleEntity role) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setVerified(true);
        user.setEncryptedPassword(bCryptPasswordEncoder.encode("1234"));
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
