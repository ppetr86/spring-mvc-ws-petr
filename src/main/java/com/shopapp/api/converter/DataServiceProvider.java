package com.shopapp.api.converter;

import com.shopapp.service.*;

public interface DataServiceProvider {

    AddressDao addressDao();

    AuthorityDao authorityDao();

    BrandDao brandDao();

    CategoryDao categoryDao();

    CreditCardDao creditCardDao();

    ProductDao productDao();

    ProductDetailDao productDetailDao();

    ProductImageDao productImageDao();

    UserDao userDao();

}
