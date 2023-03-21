package com.shopapp.service.impl;

import com.shopapp.data.entity.PasswordResetTokenEntity;
import com.shopapp.repository.PasswordResetTokenRepository;
import com.shopapp.service.PasswordTokenDao;
import com.shopapp.service.impl.superclass.AbstractIdDaoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PasswordTokenDaoImpl extends AbstractIdDaoImpl<PasswordResetTokenEntity> implements PasswordTokenDao<PasswordResetTokenEntity> {

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
