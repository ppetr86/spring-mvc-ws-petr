package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.data.entity.PasswordResetTokenEntity;
import com.appsdeveloperblog.app.ws.repository.PasswordResetTokenRepository;
import com.appsdeveloperblog.app.ws.service.PasswordTokenService;
import com.appsdeveloperblog.app.ws.service.impl.superclass.AbstractIdDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PasswordTokenDaoImpl extends AbstractIdDaoImpl<PasswordResetTokenEntity> implements PasswordTokenService<PasswordResetTokenEntity> {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public Class<PasswordResetTokenEntity> getPojoClass() {
        return PasswordResetTokenEntity.class;
    }

    @Override
    public PasswordResetTokenRepository getRepository() {
        return this.passwordResetTokenRepository;
    }
}
