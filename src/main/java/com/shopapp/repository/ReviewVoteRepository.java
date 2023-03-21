package com.shopapp.repository;

import com.shopapp.data.entity.ReviewVote;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReviewVoteRepository extends IdBasedRepository<ReviewVote> {

	@Query("SELECT v FROM ReviewVote v WHERE v.review.id = ?1 AND v.customer.id = ?2")
	public ReviewVote findByReviewAndCustomer(UUID reviewId, UUID customerId);

	@Query("SELECT v FROM ReviewVote v WHERE v.review.product.id = ?1 AND v.customer.id = ?2")
	public List<ReviewVote> findByProductAndCustomer(UUID productId, UUID customerId);
	
}
