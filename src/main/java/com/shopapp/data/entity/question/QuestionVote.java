package com.shopapp.data.entity.question;

import com.shopapp.data.entity.UserEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "questions_votes")
@Getter
@Setter
public class QuestionVote extends IdBasedEntity implements Serializable {

    public static final int VOTE_UP_POINT = 1;
    public static final int VOTE_DOWN_POINT = -1;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private long votes;

    @Transient
    public boolean isDownvoted() {
        return this.votes == VOTE_DOWN_POINT;
    }

    @Transient
    public boolean isUpvoted() {
        return this.votes == VOTE_UP_POINT;
    }

    public void voteDown() {
        this.votes = VOTE_DOWN_POINT;
    }

    public void voteUp() {
        this.votes = VOTE_UP_POINT;
    }
}
