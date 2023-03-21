package com.shopapp.repository;

import com.shopapp.data.entity.AuthenticationType;
import com.shopapp.data.entity.CustomerEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CustomerRepository extends IdBasedRepository<CustomerEntity> {

    boolean existsByEmail(String email);

    @Query("SELECT c FROM CustomerEntity c WHERE c.email = ?1")
    CustomerEntity findByEmail(String email);

    @Query("UPDATE CustomerEntity c SET c.isVerified = true WHERE c.id = ?1")
    @Modifying
    void setIsVerified(UUID id);

    @Transactional
    @Modifying
    @Query(value = "update customers c set c.is_verified=:isVerified WHERE c.id=:id", nativeQuery = true)
    void updateCustomerIsVerified(@Param("isVerified") boolean isVerified, @Param("id") UUID id);

    @Query("UPDATE CustomerEntity c SET c.authenticationType = ?2 WHERE c.id = ?1")
    @Modifying
    void updateAuthenticationType(UUID CustomerEntityId, AuthenticationType type);


    CustomerEntity findCustomerByEmailVerificationToken(String token);
}
