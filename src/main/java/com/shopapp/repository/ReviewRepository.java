package com.shopapp.repository;

import com.shopapp.data.entity.Review;
import com.shopapp.data.entity.product.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ReviewRepository extends IdBasedRepository<Review> {

    @Query("SELECT COUNT(r.id) FROM Review r WHERE r.user.id = ?1 AND "
            + "r.product.id = ?2")
    Long countByCustomerAndProduct(UUID customerId, UUID productId);

    @Query("SELECT r FROM Review r WHERE r.user.id = ?1 AND ("
            + "r.headline LIKE %?2% OR r.comment LIKE %?2% OR "
            + "r.product.name LIKE %?2%)")
    Page<Review> findByCustomer(UUID customerId, String keyword, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.user.id = ?1")
    Page<Review> findByCustomer(UUID customerId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.user.id = ?1 AND r.id = ?2")
    Review findByCustomerAndId(UUID customerId, UUID reviewId);

    Page<Review> findByProduct(ProductEntity product, Pageable pageable);

    @Query("SELECT r.votes FROM Review r WHERE r.id = ?1")
    Integer getVoteCount(UUID reviewId);

    @Query("UPDATE Review r SET r.votes = COALESCE((SELECT SUM(v.votes) FROM ReviewVote v"
            + " WHERE v.review.id=?1), 0) WHERE r.id = ?1")
    @Modifying
    void updateVoteCount(UUID reviewId);
}
