package com.shopapp.data.entity.question;

import com.shopapp.data.entity.UserEntity;
import com.shopapp.data.entity.product.ProductEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "questions")
@NoArgsConstructor
@Getter
@Setter
public class Question extends IdBasedEntity implements Serializable {

    @Column(name = "question")
    private String questionContent;

    private String answer;

    private long votes;

    private boolean approved;

    @Column(name = "ask_time")
    private Date askTime;

    @Column(name = "answer_time")
    private Date answerTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "answerer_id")
    private UserEntity answerer;

    @ManyToOne
    @JoinColumn(name = "asker_id")
    private UserEntity asker;

    @Transient
    private boolean upvotedByCurrentCustomer;

    @Transient
    private boolean downvotedByCurrentCustomer;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Question other = (Question) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Transient
    public String getAnswererFullName() {
        if (answerer != null) {
            return answerer.getFullName();
        }
        return "";
    }

    @Transient
    public String getAskerFullName() {
        return asker.getAddress().getFirstName() + " " + asker.getAddress().getLastName();
    }

    @Transient
    public String getProductName() {
        return this.product.getName();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Transient
    public boolean isAnswered() {
        return this.answer != null && !answer.isEmpty();
    }
}
