package com.appsdeveloperblog.app.ws.service;

public interface EncryptionService {

    String encrypt(String freeText);

    String decrypt(String encryptedText);
}
