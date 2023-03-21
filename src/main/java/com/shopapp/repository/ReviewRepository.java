package com.shopapp.repository;

import com.shopapp.data.entity.Review;
import com.shopapp.data.entity.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ReviewRepository extends IdBasedRepository<Review> {

	@Query("SELECT r FROM Review r WHERE r.customer.id = ?1")
	public Page<Review> findByCustomer(UUID customerId, Pageable pageable);

	@Query("SELECT r FROM Review r WHERE r.customer.id = ?1 AND ("
			+ "r.headline LIKE %?2% OR r.comment LIKE %?2% OR "
			+ "r.product.name LIKE %?2%)")
	public Page<Review> findByCustomer(UUID customerId, String keyword, Pageable pageable);

	@Query("SELECT r FROM Review r WHERE r.customer.id = ?1 AND r.id = ?2")
	public Review findByCustomerAndId(UUID customerId, UUID reviewId);
	
	public Page<Review> findByProduct(ProductEntity product, Pageable pageable);
	
	@Query("SELECT COUNT(r.id) FROM Review r WHERE r.customer.id = ?1 AND "
			+ "r.product.id = ?2")
	public Long countByCustomerAndProduct(UUID customerId, UUID productId);
	
	@Query("UPDATE Review r SET r.votes = COALESCE((SELECT SUM(v.votes) FROM ReviewVote v"
			+ " WHERE v.review.id=?1), 0) WHERE r.id = ?1")
	@Modifying
	public void updateVoteCount(UUID reviewId);
	
	@Query("SELECT r.votes FROM Review r WHERE r.id = ?1")
	public Integer getVoteCount(UUID reviewId);
}
