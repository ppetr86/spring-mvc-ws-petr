package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.AddressEntity;
import com.appsdeveloperblog.app.ws.data.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@Repository
public interface AddressRepository extends IdBasedRepository<AddressEntity> {
    List<AddressEntity> findAll();


    Page<AddressEntity> findAll(Pageable pageable);


    Stream<AddressEntity> findAllByAddressIdContainsIgnoreCase(String addressIdContains);


    @Async
    @Query("select add from AddressEntity add where add.user=:user")
    Future<List<AddressEntity>> findAllByUser(UserEntity user);


    AddressEntity findByAddressId(String addressId);


    @Async
    Future<AddressEntity> findFirstByCityEqualsIgnoreCase(String cityName);

}
