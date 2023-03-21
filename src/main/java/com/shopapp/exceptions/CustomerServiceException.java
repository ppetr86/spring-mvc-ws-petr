package com.shopapp.exceptions;

public class CustomerServiceException extends RuntimeException {

    public CustomerServiceException(String message) {
        super(message);
    }
}
