package com.shopapp.repository;

import com.shopapp.data.entity.AuthorityEntity;
import com.shopapp.shared.Authority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends IdBasedRepository<AuthorityEntity> {
    AuthorityEntity findByName(Authority value);

    @Query("SELECT a from AuthorityEntity a where a.name = :value")
    AuthorityEntity findByName(@Param("value") String value);
}
