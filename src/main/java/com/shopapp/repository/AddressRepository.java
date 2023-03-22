package com.shopapp.repository;

import com.shopapp.data.entity.AddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@Repository
public interface AddressRepository extends IdBasedRepository<AddressEntity> {
    List<AddressEntity> findAll();

    Page<AddressEntity> findAll(Pageable pageable);

    Stream<AddressEntity> findAllByCityLikeIgnoreCase(String city);

    @Async
    Future<AddressEntity> findFirstByCityEqualsIgnoreCase(String cityName);

}
