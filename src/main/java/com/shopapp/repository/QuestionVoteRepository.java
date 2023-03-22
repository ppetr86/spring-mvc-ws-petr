package com.shopapp.repository;

import com.shopapp.data.entity.question.QuestionVote;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface QuestionVoteRepository extends IdBasedRepository<QuestionVote> {

    @Query("SELECT qv FROM QuestionVote qv WHERE qv.question.product.id = ?1 AND qv.user.id = ?2")
    List<QuestionVote> findByProductAndUser(UUID productId, UUID userId);

    @Query("SELECT qv FROM QuestionVote qv WHERE qv.question.id = ?1 AND qv.user.id = ?2")
    QuestionVote findByQuestionAndUser(UUID questionId, UUID userId);

}
