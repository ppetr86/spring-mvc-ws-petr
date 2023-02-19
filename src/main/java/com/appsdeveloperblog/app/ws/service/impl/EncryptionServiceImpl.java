package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.service.EncryptionService;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Created by jt on 6/28/22.
 */
@Service
public class EncryptionServiceImpl implements EncryptionService {
    @Override
    public String decrypt(final String encryptedText) {
        return new String(Base64.getDecoder().decode(encryptedText));
    }

    @Override
    public String encrypt(final String freeText) {
        return Base64.getEncoder().encodeToString(freeText.getBytes(StandardCharsets.UTF_8));
    }
}
