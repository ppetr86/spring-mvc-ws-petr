package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
public class UserEntity extends IdBasedTimeRevisionEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5313493413859894403L;

    @Column(nullable = false, updatable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    private String emailVerificationToken;

    @Column(nullable = false)
    private boolean isVerified;

    //persist because if I delete user I dont want to delete role
    //fetch eager so that when user is read from DB, roles will be read too
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "user")
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "user")
    private Set<AddressEntity> addresses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "user")
    private Set<CreditCardEntity> creditCards = new HashSet<>();


    protected UserEntity(UUID id) {
        super(id);
    }

    public UserEntity() {
        super();
    }

    public UserEntity(String userId, String firstName,
                      String lastName, String email, String encryptedPassword,
                      String emailVerificationToken, Boolean isVerified,
                      Set<RoleEntity> roles, Set<AddressEntity> addresses, Set<CreditCardEntity> creditCards,
                      UUID id, Long revision, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, revision, createdAt, updatedAt);
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.encryptedPassword = encryptedPassword;
        this.emailVerificationToken = emailVerificationToken;
        this.isVerified = isVerified;
        this.roles = roles;
        this.addresses = addresses;
        this.creditCards = creditCards;
    }


    public int compareTo(final UserEntity o) {
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
