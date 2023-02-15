package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "credit_cards")
@Getter
@Setter
public class CreditCardEntity extends IdBasedTimeRevisionEntity {

    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid")
    private UserEntity user;

    protected CreditCardEntity(UUID id) {
        super(id);
    }

    public CreditCardEntity() {
        super();
    }

    @Override
    public boolean equals(final Object obj) {
        return super.equalsId(obj);
    }

    @Override
    public int hashCode() {
        return hashCodeId();
    }

    @PrePersist
    public void prePersistCallback() {
        System.out.println("JPA PrePresist CreditCardEntity Callback was called");
    }

}
