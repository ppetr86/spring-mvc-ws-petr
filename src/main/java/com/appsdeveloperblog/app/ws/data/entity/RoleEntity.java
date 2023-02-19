package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.shared.Roles;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity extends IdBasedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5605260522147928803L;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Roles name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "roles_authorities",
            joinColumns = @JoinColumn(name = "role", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority", referencedColumnName = "id"))
    private Set<AuthorityEntity> authorities = new HashSet<>();

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<UserEntity> users = new HashSet<>();

    public RoleEntity() {
        super();
    }

    protected RoleEntity(UUID id) {
        super(id);
    }

    public void addAuthority(AuthorityEntity value) {
        if (value != null && !this.authorities.contains(value)) {
            this.authorities.add(value);
            value.getRoles().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleEntity that)) return false;
        return super.equalsId(o) || name == that.name;
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(name);
    }

    public void removeAuthority(AuthorityEntity value) {
        if (value != null && this.authorities.contains(value)) {
            this.authorities.remove(value);
            value.getRoles().remove(this);
        }
    }
}
