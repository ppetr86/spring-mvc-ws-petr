package com.appsdeveloperblog.app.ws.io.repository;

import com.appsdeveloperblog.app.ws.io.entity.PasswordResetTokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface PasswordResetTokenRepository extends SearchRepository<PasswordResetTokenEntity, UUID>{
	PasswordResetTokenEntity findByToken(String token);
}
