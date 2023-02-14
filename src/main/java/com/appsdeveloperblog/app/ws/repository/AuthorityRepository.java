package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.AuthorityEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends IdBasedRepository<AuthorityEntity> {
    AuthorityEntity findByName(String name);
}
