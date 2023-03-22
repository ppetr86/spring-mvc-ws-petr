package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.shopapp.shared.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserEntity extends IdBasedTimeRevisionEntity implements Serializable {

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    private String emailVerificationToken;

    @Column(nullable = false)
    private boolean isVerified;

    @Column(length = 64)
    private String photos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
    private Set<RoleEntity> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private AddressEntity address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user")
    private Set<CreditCardEntity> creditCards = new HashSet<>();

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

    @Transient
    public String getFullName() {
        return this.address.firstName + " " + this.address.lastName;
    }

    @Transient
    public String getPhotosImagePath() {
        if (id == null || photos == null) return "/images/default-user.png";

        return Constants.S3_BASE_URI + "/user-photos/" + this.id + "/" + this.photos;
    }

    public boolean hasRole(String roleName) {
        Iterator<RoleEntity> iterator = roles.iterator();

        while (iterator.hasNext()) {
            RoleEntity role = iterator.next();
            if (role.getName().equals(roleName)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(email);
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


    /*@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinTable(name = "customers_addresses",
			joinColumns = @JoinColumn(name = "customer", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "address", referencedColumnName = "id"))
	private Set<AddressEntity> addresses = new HashSet<>();*/

	/*public void addAddress(AddressEntity value) {
		if (value != null && !this.addresses.contains(value)) {
			this.addresses.add(value);
			value.getCustomers().add(this);
		}
	}*/


	/*public void removeAddress(AddressEntity value) {
		if (value != null && this.addresses.contains(value)) {
			this.addresses.remove(value);
			value.setCustomers(null);
		}
	}*/
}
