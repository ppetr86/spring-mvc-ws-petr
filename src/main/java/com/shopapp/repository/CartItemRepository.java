package com.shopapp.repository;

import com.shopapp.data.entity.CartItemEntity;
import com.shopapp.data.entity.CustomerEntity;
import com.shopapp.data.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends IdBasedRepository<CartItemEntity> {
	
	public List<CartItemEntity> findByCustomer(CustomerEntity customer);

	public CartItemEntity findByCustomerAndProduct(CustomerEntity customer, ProductEntity product);

	@Modifying
	@Query("UPDATE CartItemEntity c SET c.quantity = ?1 WHERE c.customer.id = ?2 AND c.product.id = ?3")
	public void updateQuantity(Integer quantity, UUID customerId, UUID productId);

	@Modifying
	@Query("DELETE FROM CartItemEntity c WHERE c.customer.id = ?1 AND c.product.id = ?2")
	public void deleteByCustomerAndProduct(UUID customerId, Integer productId);
	
	@Modifying
	@Query("DELETE CartItemEntity c WHERE c.customer.id = ?1")
	public void deleteByCustomer(UUID customerId);
}
