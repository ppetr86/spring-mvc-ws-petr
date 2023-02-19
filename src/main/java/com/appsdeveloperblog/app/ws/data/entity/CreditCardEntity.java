package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "credit_cards")
@Getter
@Setter
public class CreditCardEntity extends IdBasedTimeRevisionEntity {

    @Column(name = "credit_card_number", nullable = false)
    private String creditCardNumber;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(name = "expiration_date", nullable = false)
    private String expirationDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user")
    private UserEntity user;

    public CreditCardEntity() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCardEntity that)) return false;
        if (!super.equals(o)) return false;
        return super.equalsId(o) || creditCardNumber.equals(that.creditCardNumber) && cvv.equals(that.cvv) && expirationDate.equals(that.expirationDate) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(creditCardNumber, cvv, expirationDate, user);
    }

    @PrePersist
    public void prePersistCallback() {
        System.out.println("JPA PrePresist CreditCardEntity Callback was called");
    }

}
