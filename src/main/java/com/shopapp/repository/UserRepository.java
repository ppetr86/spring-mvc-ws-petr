package com.shopapp.repository;

import com.shopapp.data.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends IdBasedTimeRevisionRepository<UserEntity> {

    boolean existsByEmail(String email);

    @Query(value = "SELECT * from Users u WHERE u.is_verified = 'true'",
            countQuery = "SELECT count(*) from Users u WHERE u.is_verified = 'true'",
            nativeQuery = true)
    Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);

    UserEntity findByEmail(String email);

    UserEntity findUserByEmailVerificationToken(String token);

    //using positional parameters, order matters
    @Query(value = "SELECT * from Users u WHERE u.address.first_name = ?1", nativeQuery = true)
    List<UserEntity> findUserByFirstName(String firstName);

    @Query(value = "SELECT * from Users u WHERE u.address.last_name = :lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName") String lastName);

    //using name parameters, param order does not matter
    //using JPQL
    @Query(value = "SELECT u.address.first_name, u.address.last_name from users u WHERE u.address.first_name LIKE '%:keyword%' or u.address.last_name LIKE '%:keyword%'", nativeQuery = true)
    List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT * from users u WHERE u.address.first_name LIKE '%:keyword%' or u.address.last_name LIKE '%:keyword%'", nativeQuery = true)
    List<UserEntity> findUsersByKeyword(@Param("keyword") String keyword);

    //SELECTing only some fields using JPQL and named param
    @Query("SELECT user.address.firstName, user.address.lastName from UserEntity user WHERE user.id =:id")
    List<Object[]> getUserEntityFullNameById(@Param("id") UUID id);

    //using JPQL java persistence query language... refering to
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u set u.isVerified =:isVerified WHERE u.id = :id")
    void updateUserEntityIsVerified(@Param("isVerified") boolean isVerified, @Param("id") UUID id);

    @Transactional
    @Modifying
    @Query(value = "update users u set u.is_verified=:isVerified WHERE u.id=:id", nativeQuery = true)
    void updateUserIsVerified(@Param("isVerified") boolean isVerified, @Param("id") UUID id);
}
