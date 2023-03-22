package com.shopapp.data.entity;

import com.shopapp.data.entity.product.ProductEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
public class Review extends IdBasedEntity {

    @Column(length = 128, nullable = false)
    private String headline;

    @Column(length = 300, nullable = false)
    private String comment;

    private long rating;

    @Column(nullable = false)
    private Date reviewTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private long votes;

    @Transient
    private boolean upvotedByCurrentCustomer;

    @Transient
    private boolean downvotedByCurrentCustomer;

    public Review(UUID id) {

        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Review other = (Review) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Review [headline=" + headline + ", rating=" + rating + ", reviewTime=" + reviewTime + ", product="
                + product.getShortName() + ", customer=" + user.getFullName() + "]";
    }
}
