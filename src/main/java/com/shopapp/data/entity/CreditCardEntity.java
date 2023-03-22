package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.shopapp.shared.dto.CreditCardDtoIn;
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
    @JoinColumn(name = "customer")
    private CustomerEntity customer;

    public CreditCardEntity() {
        super();
    }

    public CreditCardEntity(CreditCardDtoIn each, CustomerEntity customer) {
        this.setCvv(each.getCvv());
        this.setCreditCardNumber(each.getCreditCardNumber());
        this.setCustomer(customer);
        this.setExpirationDate(each.getExpirationDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCardEntity that)) return false;
        if (!super.equals(o)) return false;
        return super.equalsId(o) || creditCardNumber.equals(that.creditCardNumber) && cvv.equals(that.cvv) && expirationDate.equals(that.expirationDate) && Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(creditCardNumber, cvv, expirationDate, customer);
    }

    @PrePersist
    public void prePersistCallback() {
        System.out.println("JPA PrePresist CreditCardEntity Callback was called");
    }

}
