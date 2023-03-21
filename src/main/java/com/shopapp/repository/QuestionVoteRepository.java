package com.shopapp.repository;

import com.shopapp.data.entity.question.QuestionVote;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface QuestionVoteRepository extends IdBasedRepository<QuestionVote> {

	@Query("SELECT qv FROM QuestionVote qv WHERE qv.question.id = ?1 AND qv.customer.id = ?2")
	public QuestionVote findByQuestionAndCustomer(UUID questionId, UUID customerId);

	@Query("SELECT qv FROM QuestionVote qv WHERE qv.question.product.id = ?1 AND qv.customer.id = ?2")
	public List<QuestionVote> findByProductAndCustomer(UUID productId, UUID customerId);

}
