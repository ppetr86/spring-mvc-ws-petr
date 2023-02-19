package com.appsdeveloperblog.app.ws;

import com.appsdeveloperblog.app.ws.data.Brands;
import com.appsdeveloperblog.app.ws.data.Categories;
import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.data.entity.AuthorityEntity;
import com.appsdeveloperblog.app.ws.data.entity.BrandEntity;
import com.appsdeveloperblog.app.ws.data.entity.CategoryEntity;
import com.appsdeveloperblog.app.ws.data.entity.ProductEntity;
import com.appsdeveloperblog.app.ws.data.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import com.appsdeveloperblog.app.ws.service.AddressService;
import com.appsdeveloperblog.app.ws.service.AuthorityService;
import com.appsdeveloperblog.app.ws.service.BrandService;
import com.appsdeveloperblog.app.ws.service.CategoryService;
import com.appsdeveloperblog.app.ws.service.ProductDetailService;
import com.appsdeveloperblog.app.ws.service.ProductService;
import com.appsdeveloperblog.app.ws.service.RoleService;
import com.appsdeveloperblog.app.ws.service.UserService;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecification;
import com.appsdeveloperblog.app.ws.service.specification.GenericSpecificationsBuilder;
import com.appsdeveloperblog.app.ws.shared.Authority;
import com.appsdeveloperblog.app.ws.shared.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class InitialSetup {

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

    private AuthorityService authorityService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AddressService addressService;
    private BrandService brandService;
    private CategoryService categoryService;
    private ProductService productService;
    private ProductDetailService productDetailService;
    private RoleService<RoleEntity> roleService;
    private UserService userService;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {

        var faker = new Faker();

        //create authorities which are authorization
        for (var each : Authority.values()) {
            createAuthority(each);
            createAuthority(each);
            createAuthority(each);
        }

        //create roles which depend on authorities
        //users depend on roles
        createRoles();

        //create addresses which I will set on users...
        int targetCountOfAddresses = 50;
        if (addressService.getCount() >= targetCountOfAddresses)
            System.out.println("From Application ready event addresses...Will not create");
        else {
            System.out.println("From Application ready event addresses...Creating");
            createAddresses(faker, targetCountOfAddresses);
            //CompletableFuture.runAsync(() -> createAddresses(faker, targetCountOfAddresses));
        }

        //users depend on roles, authorities and addresses
        int targetCountOfUsers = 50;
        if (userService.getCount() >= targetCountOfUsers)
            System.out.println("From Application ready event users...Will not create");
        else {
            System.out.println("From Application ready event users...Creating");
            createUsers();
            //CompletableFuture.runAsync(this::createUsers);
        }

        int targetCountOfCategories = Categories.values().length;
        if (categoryService.getCount() >= targetCountOfCategories)
            System.out.println("From Application ready event categories...Will not create");
        else {
            System.out.println("From Application ready event categories...Creating");
            createCategories();
        }

        //brands depend on categories
        int targetCountOfBrands = Brands.values().length;
        if (brandService.getCount() >= targetCountOfBrands)
            System.out.println("From Application ready event brands...Will not create");
        else {
            System.out.println("From Application ready event brands...Creating");
            createBrands();
        }

        //products depend on brands and categories
        int targetCountOfProducts = 100;
        if (productService.getCount() >= targetCountOfProducts)
            System.out.println("From Application ready event products...Will not create...");
        else {
            System.out.println("From Application ready event products...Creating");
            createProductsFromExternalApi();
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

            categories.forEach(currentBrand::addCategory);
            brandService.save(currentBrand);
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
                //each is child, add child to category
                category.setHasChildren(true);
                each.getChildren().stream()
                        .map(child -> categoryService.findByName(child.getDescription()))
                        .forEach(category::addChildCategory);
            }
            categoryService.save(category);
        }
    }


    private void createRandomUsers(int quantity, RoleEntity role) {

        var addressList = new ArrayList<>(addressService.loadAll());
        var randomObj = new Random();
        var faker = new Faker();

        for (int i = 0; i < quantity; i++) {
            createUser(role, addressList, randomObj, faker);
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
        currentUser.addAddress(randomAddress);

        if (!userService.existsByEmail(currentUser.getEmail())) {
            userService.save(currentUser);
        }
    }

    private void createAddresses(Faker faker, int addressCount) {


        var kocianovaAddress = new AddressEntity("Praha", "Czech republic", "Kocianova", "155 00");
        addressService.save(kocianovaAddress);


        var addressEntities = addressService.loadAll();

        for (int i = 0; i < addressCount; i++) {
            var zipCode = faker.address().zipCode();
            while (zipCode.length() > 7) {
                zipCode = faker.address().zipCode();
            }

            var country = faker.address().country();
            while (country.length() > 15) {
                country = faker.address().country();
            }

            var streetName = faker.address().streetName();
            while (streetName.length() > 100) {
                streetName = faker.address().streetName();
            }

            var city = faker.address().city();
            while (city.length() > 15) {
                city = faker.address().city();
            }

            var addressEntity = new AddressEntity();
            addressEntity.setCity(city);
            addressEntity.setCountry(country);
            addressEntity.setStreet(streetName);
            addressEntity.setPostalCode(zipCode);

            if (!addressEntities.contains(addressEntity)) {
                addressService.save(addressEntity);
            }
        }
    }

    private AuthorityEntity createAuthority(Authority value) {
        var entity = authorityService.findByAuthority(value);
        if (entity != null)
            return entity;

        entity = new AuthorityEntity();
        entity.setName(value);
        entity = authorityService.save(entity);
        return entity;
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
                    if (brand == null) {
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

    private RoleEntity createRole(Roles name, Set<AuthorityEntity> authorities) {
        var role = roleService.findByName(name);
        if (role == null)
            role = new RoleEntity();

        role.setName(name);
        for (AuthorityEntity each : authorities) {
            role.addAuthority(each);
        }

        role = roleService.save(role);
        return role;
    }

    private void createRoles() {
        var readAuthority = authorityService.findByAuthority(Authority.READ_AUTHORITY);
        var writeAuthority = authorityService.findByAuthority(Authority.WRITE_AUTHORITY);
        var deleteAuthority = authorityService.findByAuthority(Authority.DELETE_AUTHORITY);

        createRole(Roles.ROLE_ADMIN, Set.of(readAuthority, writeAuthority, deleteAuthority));
        createRole(Roles.ROLE_USER, Set.of(readAuthority, writeAuthority));
    }

    private void createUsers() {

        var roleAdmin = roleService.findByName(Roles.ROLE_ADMIN);
        var petr = new UserEntity();
        var specBuilder = new GenericSpecificationsBuilder<AddressEntity>();
        specBuilder.with("street", GenericSpecification.SearchOperation.EQUALITY, List.of("Kocianova"));
        var addressPraha = addressService.findOneBy(specBuilder.build()).orElse(null);

        setUserProperties(petr, "Petr", "Novotny", "petr@test.com", roleAdmin, addressPraha);
        if (!userService.existsByEmail("petr@test.com")) {
            userService.save(petr);
        }

        var roleUser = roleService.findByName(Roles.ROLE_USER);
        var ivana = new UserEntity();
        setUserProperties(ivana, "Ivana", "Michalova", "ivana@test.com", roleUser, addressPraha);
        if (!userService.existsByEmail("ivana@test.com")) {
            userService.save(ivana);
        }

        var test = new UserEntity();
        setUserProperties(test, "test_name", "test_name", "test_email@test.com", roleUser, addressPraha);
        if (!userService.existsByEmail("test_email@test.com")) {
            userService.save(test);
        }

        createRandomUsers(50, roleUser);
    }


    private void setUserProperties(UserEntity user, String firstName, String lastName, String email, RoleEntity role, AddressEntity address) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setVerified(true);
        user.setEncryptedPassword(bCryptPasswordEncoder.encode("1234"));
        user.addRole(role);
        user.addAddress(address);
    }

}
