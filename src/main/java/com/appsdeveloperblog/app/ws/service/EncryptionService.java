package com.appsdeveloperblog.app.ws.service;

public interface EncryptionService {

    String decrypt(String encryptedText);


    String encrypt(String freeText);
}
