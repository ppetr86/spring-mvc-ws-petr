package com.shopapp.repository;

import com.shopapp.data.entity.UserEntity;
import com.shopapp.data.entity.order.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OrderRepository extends IdBasedRepository<OrderEntity> {

    @Query("SELECT DISTINCT o FROM OrderEntity o JOIN o.orderDetails od JOIN od.product p "
            + "WHERE o.user.id = ?2 "
            + "AND (p.name LIKE %?1% OR o.status LIKE %?1%)")
    Page<OrderEntity> findAll(String keyword, UUID userId, Pageable pageable);

    @Query("SELECT o FROM OrderEntity o WHERE o.user.id = ?1")
    Page<OrderEntity> findAll(UUID userId, Pageable pageable);

    OrderEntity findByIdAndUser(UUID id, UserEntity user);

}
