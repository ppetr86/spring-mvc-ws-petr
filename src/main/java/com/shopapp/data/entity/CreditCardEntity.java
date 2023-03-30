package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.shopapp.data.entitydto.in.CreditCardDtoIn;
import jakarta.persistence.*;
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

    public CreditCardEntity(CreditCardDtoIn each, UserEntity user) {
        this.setCvv(each.getCvv());
        this.setCreditCardNumber(each.getCreditCardNumber());
        this.setUser(user);
        this.setExpirationDate(each.getExpirationDate());
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
