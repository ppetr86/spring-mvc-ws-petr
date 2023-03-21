package com.shopapp.repository;

import com.shopapp.data.entity.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface QuestionRepository extends IdBasedRepository<Question> {

	@Query("SELECT q FROM Question q WHERE q.approved = true AND q.product.id = ?1")
	Page<Question> findAll(UUID productId, Pageable pageable);
	
	@Query("SELECT q FROM Question q WHERE q.approved = true AND q.product.alias = ?1")
	Page<Question> findByAlias(String alias, Pageable pageable);
	
	@Query("SELECT COUNT (q) FROM Question q WHERE q.approved = true and q.product.id =?1")
	int countApprovedQuestions(UUID productId);
	
	@Query("SELECT q FROM Question q WHERE q.asker.id = ?1")
	Page<Question> findByCustomer(UUID customerId, Pageable pageable);

	@Query("SELECT q FROM Question q WHERE q.asker.id = ?1 AND ("
			+ "q.questionContent LIKE %?2% OR "
			+ "q.answer LIKE %?2% OR q.product.name LIKE %?2%)")
	Page<Question> findByCustomer(UUID customerId, String keyword, Pageable pageable);

	@Query("SELECT q FROM Question q WHERE q.asker.id = ?1 AND q.id = ?2")
	Question findByCustomerAndId(UUID customerId, UUID questionId);
	
	@Query("UPDATE Question q SET q.votes = COALESCE((SELECT SUM(v.votes) FROM QuestionVote v"
			+ " WHERE v.question.id=?1), 0) WHERE q.id = ?1")
	@Modifying
	public void updateVoteCount(UUID questionId);

	@Query("SELECT q.votes FROM Question q WHERE q.id = ?1")
	public Integer getVoteCount(UUID questionId);
	
	@Query("SELECT COUNT (q) FROM Question q WHERE q.approved = true AND q.answer IS NOT NULL and q.product.id =?1")
	int countAnsweredQuestions(UUID productId);
}
