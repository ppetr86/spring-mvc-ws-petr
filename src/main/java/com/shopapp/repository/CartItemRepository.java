package com.shopapp.repository;

import com.shopapp.data.entity.CartItemEntity;
import com.shopapp.data.entity.UserEntity;
import com.shopapp.data.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends IdBasedRepository<CartItemEntity> {

    @Modifying
    @Query("DELETE CartItemEntity c WHERE c.user.id = ?1")
    void deleteByCustomer(UUID customerId);

    @Modifying
    @Query("DELETE FROM CartItemEntity c WHERE c.user.id = ?1 AND c.product.id = ?2")
    void deleteByCustomerAndProduct(UUID customerId, Integer productId);

    List<CartItemEntity> findByUser(UserEntity customer);

    CartItemEntity findByUserAndProduct(UserEntity customer, ProductEntity product);

    @Modifying
    @Query("UPDATE CartItemEntity c SET c.quantity = ?1 WHERE c.user.id = ?2 AND c.product.id = ?3")
    void updateQuantity(Integer quantity, UUID customerId, UUID productId);
}
