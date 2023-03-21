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
public class UserEntity extends IdBasedTimeRevisionEntity implements Serializable{

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
	
	@Column(length = 64)
	private String photos;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "users_roles",
			joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
	private Set<RoleEntity> roles = new HashSet<>();

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



	public void removeRole(RoleEntity value) {
		if (value != null && this.roles.contains(value)) {
			this.roles.remove(value);
			value.getUsers().remove(this);
		}
	}

	
	@Transient
	public String getPhotosImagePath() {
		if (id == null || photos == null) return "/images/default-user.png";

		return Constants.S3_BASE_URI + "/user-photos/" + this.id + "/" + this.photos;
	}
	
	@Transient
	public String getFullName() {
		return firstName + " " + lastName;
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
}
