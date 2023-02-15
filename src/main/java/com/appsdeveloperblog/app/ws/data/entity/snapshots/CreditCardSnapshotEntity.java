package com.appsdeveloperblog.app.ws.data.entity.snapshots;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "credit_cards_snapshots")
@Getter
@Setter
public class CreditCardSnapshotEntity extends IdBasedTimeSnapshotEntity {

    private String creditCardNumber;

    private String cvv;

    private String expirationDate;

    protected CreditCardSnapshotEntity(UUID id) {
        super(id);
    }

    public CreditCardSnapshotEntity() {
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
        System.out.println("JPA PrePresist CreditCardSnapshotEntity Callback was called");
    }

}
