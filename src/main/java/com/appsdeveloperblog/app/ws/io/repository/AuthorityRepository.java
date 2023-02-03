package com.appsdeveloperblog.app.ws.io.repository;

import com.appsdeveloperblog.app.ws.io.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface AuthorityRepository extends SearchRepository<AuthorityEntity, UUID> {
	AuthorityEntity findByName(String name);
}
