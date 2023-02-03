package com.appsdeveloperblog.app.ws.io.repository;

import com.appsdeveloperblog.app.ws.io.entity.address.AddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface AddressRepository extends SearchRepository<AddressEntity, UUID> {
    AddressEntity findByAddressId(String addressId);

    List<AddressEntity> findAll();

    Page<AddressEntity> findAll(Pageable pageable);

}
