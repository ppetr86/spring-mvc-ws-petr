package com.shopapp.api.converter;

import com.shopapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DataServiceProviderImpl implements DataServiceProvider {

    private AddressDao addressDao;

    private AuthorityDao authorityDao;

    private BrandDao brandDao;

    private CategoryDao categoryDao;

    private CreditCardDao creditCardDao;

    private CustomerDao customerDao;

    private ProductDao productDao;

    private ProductDetailDao productDetailDao;

    private ProductImageDao productImageDao;

    private UserDao userDao;

    @Override
    public AddressDao addressDao() {
        return addressDao;
    }

    @Override
    public AuthorityDao authorityDao() {
        return authorityDao;
    }

    @Override
    public BrandDao brandDao() {
        return brandDao;
    }

    @Override
    public CategoryDao categoryDao() {
        return categoryDao;
    }


    @Override
    public CreditCardDao creditCardDao() {
        return creditCardDao;
    }

    @Override
    public CustomerDao customerDao() {
        return customerDao;
    }

    @Override
    public ProductDao productDao() {
        return productDao;
    }

    @Override
    public ProductDetailDao productDetailDao() {
        return productDetailDao;
    }

    @Override
    public ProductImageDao productImageDao() {
        return productImageDao;
    }

    @Override
    public UserDao userDao() {
        return userDao;
    }
}
