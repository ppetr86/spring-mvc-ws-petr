package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews_votes")
@Getter
@Setter
@NoArgsConstructor
public class ReviewVote extends IdBasedEntity {

    private static final int VOTE_UP_POINT = 1;
    private static final int VOTE_DOWN_POINT = -1;

    private long votes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @Transient
    public boolean isDownvoted() {
        return this.votes == VOTE_DOWN_POINT;
    }

    @Transient
    public boolean isUpvoted() {
        return this.votes == VOTE_UP_POINT;
    }

    @Override
    public String toString() {
        return "ReviewVote [votes=" + votes + ", customer=" + user.getFullName() +
                ", review=" + review.getId() + "]";
    }

    public void voteDown() {
        this.votes = VOTE_DOWN_POINT;
    }

    public void voteUp() {
        this.votes = VOTE_UP_POINT;
    }
}
