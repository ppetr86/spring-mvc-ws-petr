package com.shopapp.service;

public interface EncryptionService {

    String decrypt(String encryptedText);

    String encrypt(String freeText);
}
