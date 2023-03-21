package com.shopapp.repository;


import com.shopapp.data.entity.CustomerEntity;
import com.shopapp.data.entity.order.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OrderRepository extends IdBasedRepository<OrderEntity> {

	@Query("SELECT DISTINCT o FROM OrderEntity o JOIN o.orderDetails od JOIN od.product p "
			+ "WHERE o.customer.id = ?2 "
			+ "AND (p.name LIKE %?1% OR o.status LIKE %?1%)")
	public Page<OrderEntity> findAll(String keyword, UUID customerId, Pageable pageable);

	@Query("SELECT o FROM OrderEntity o WHERE o.customer.id = ?1")
	public Page<OrderEntity> findAll(UUID customerId, Pageable pageable);
	
	public OrderEntity findByIdAndCustomer(UUID id, CustomerEntity customer);
	
}
