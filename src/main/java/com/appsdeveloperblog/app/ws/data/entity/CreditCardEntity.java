package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

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

    @PrePersist
    public void prePersistCallback() {
        System.out.println("JPA PrePresist CreditCardEntity Callback was called");
    }

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

}
