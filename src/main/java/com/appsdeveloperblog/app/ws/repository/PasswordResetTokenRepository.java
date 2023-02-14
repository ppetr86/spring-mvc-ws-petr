package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.PasswordResetTokenEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends IdBasedRepository<PasswordResetTokenEntity> {
	PasswordResetTokenEntity findByToken(String token);
}
