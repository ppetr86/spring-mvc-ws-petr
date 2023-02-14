package com.appsdeveloperblog.app.ws.data.entity.snapshots;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "users_snapshots")
@Getter
@Setter
public class UserSnapshotEntity extends IdBasedTimeSnapshotEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5313493413859894403L;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    protected UserSnapshotEntity(UUID id) {
        super(id);
    }

    public UserSnapshotEntity() {
        super();
    }

    public UserSnapshotEntity(String firstName, String lastName, String email, String encryptedPassword) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
    }


    public int compareTo(final UserSnapshotEntity o) {
        return compareToId(o);
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
