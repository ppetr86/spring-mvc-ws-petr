package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
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


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
    private Set<RoleEntity> roles = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(name = "users_addresses",
            joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address", referencedColumnName = "id"))
    private Set<AddressEntity> addresses = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "user")
    private Set<CreditCardEntity> creditCards = new HashSet<>();

    protected UserEntity(UUID id) {
        super(id);
    }


    public UserEntity() {
        super();
    }

    public UserEntity(String firstName,
                      String lastName, String email, String encryptedPassword,
                      String emailVerificationToken, Boolean isVerified,
                      Set<RoleEntity> roles, Set<AddressEntity> addresses, Set<CreditCardEntity> creditCards,
                      UUID id, Long revision, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(id, revision, createdAt, updatedAt);
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

    public void addAddress(AddressEntity value) {
        if (value != null && !this.addresses.contains(value)) {
            this.addresses.add(value);
            value.getUsers().add(this);
        }
    }

    public void addCreditCard(CreditCardEntity value) {
        if (value != null && !this.creditCards.contains(value)) {
            this.creditCards.add(value);
            value.setUser(this);
        }
    }

    public void addRole(RoleEntity value) {
        if (value != null && !this.roles.contains(value)) {
            this.roles.add(value);
            value.getUsers().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        if (!super.equals(o)) return false;
        return super.equalsId(o) || email.equals(that.email);
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(email);
    }

    public void removeAddress(AddressEntity value) {
        if (value != null && this.addresses.contains(value)) {
            this.addresses.remove(value);
            value.setUsers(null);
        }
    }

    public void removeCreditCard(CreditCardEntity value) {
        if (value != null && this.creditCards.contains(value)) {
            this.creditCards.remove(value);
            value.setUser(null);
        }
    }

    public void removeRole(RoleEntity value) {
        if (value != null && this.roles.contains(value)) {
            this.roles.remove(value);
            value.getUsers().remove(this);
        }
    }

}
