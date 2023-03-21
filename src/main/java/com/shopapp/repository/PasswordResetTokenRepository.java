package com.shopapp.repository;

import com.shopapp.data.entity.PasswordResetTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends IdBasedRepository<PasswordResetTokenEntity> {
    PasswordResetTokenEntity findByToken(String token);
}
