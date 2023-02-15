package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends IdBasedTimeRevisionRepository<UserEntity> {

    void deleteByUserId(String userId);


    boolean existsByEmail(String email);


    @Query(value = "select * from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'",
            countQuery = "select count(*) from Users u where u.EMAIL_VERIFICATION_STATUS = 'true'",
            nativeQuery = true)
    Page<UserEntity> findAllUsersWithConfirmedEmailAddress(Pageable pageableRequest);


    UserEntity findByEmail(String email);


    UserEntity findByUserId(String userId);


    UserEntity findUserByEmailVerificationToken(String token);


    //using positional parameters, order matters
    @Query(value = "select * from Users u where u.first_name = ?1", nativeQuery = true)
    List<UserEntity> findUserByFirstName(String firstName);


    @Query(value = "select * from Users u where u.last_name = :lastName", nativeQuery = true)
    List<UserEntity> findUserByLastName(@Param("lastName") String lastName);


    //using name parameters, param order does not matter
    //using JPQL
    @Query("select user from UserEntity user where user.userId =:userId")
    UserEntity findUserEntityByUserId(@Param("userId") String userId);


    @Query(value = "select u.first_name, u.last_name from Users u where u.first_name LIKE '%:keyword%' or u.last_name LIKE '%:keyword%'", nativeQuery = true)
    List<Object[]> findUserFirstNameAndLastNameByKeyword(@Param("keyword") String keyword);


    @Query(value = "select * from Users u where first_name LIKE '%:keyword%' or last_name LIKE '%:keyword%'", nativeQuery = true)
    List<UserEntity> findUsersByKeyword(@Param("keyword") String keyword);


    //selecting only some fields using JPQL and named param
    @Query("select user.firstName, user.lastName from UserEntity user where user.userId =:userId")
    List<Object[]> getUserEntityFullNameById(@Param("userId") String userId);


    //using JPQL java persistence query language... refering to
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u set u.isVerified =:isVerified where u.userId = :userId")
    void updateUserEntityIsVerified(
            @Param("isVerified") boolean isVerified,
            @Param("userId") String userId);


    @Transactional
    @Modifying
    @Query(value = "update users u set u.EMAIL_VERIFICATION_STATUS=:isVerified where u.user_id=:userId", nativeQuery = true)
    void updateUserIsVerified(@Param("isVerified") boolean isVerified,
                              @Param("userId") String userId);
}
