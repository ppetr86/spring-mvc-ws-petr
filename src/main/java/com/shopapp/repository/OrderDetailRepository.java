package com.shopapp.repository;

import com.shopapp.data.entity.order.OrderDetailEntity;
import com.shopapp.data.entity.order.OrderStatusEntity;
import org.springframework.data.jpa.repository.Query;

public interface OrderDetailRepository extends IdBasedRepository<OrderDetailEntity> {

    @Query("SELECT COUNT(d) FROM OrderDetailEntity d JOIN OrderTrackEntity t ON d.order.id = t.order.id"
            + " WHERE d.product.id = ?1 AND d.order.user.id = ?2 AND"
            + " t.status = ?3")
    Long countByProductAndCustomerAndOrderStatus(
            Integer productId, Integer customerId, OrderStatusEntity status);

}
