package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RoleEntity extends IdBasedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 5605260522147928803L;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "roles_authorities",
            joinColumns = @JoinColumn(name = "role_uuid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_uuid", referencedColumnName = "id"))
    private Collection<AuthorityEntity> authorities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid")
    private UserEntity user;


    public RoleEntity(String name) {
        this.name = name;
    }

    public RoleEntity() {
        super();
    }

    protected RoleEntity(UUID id) {
        super(id);
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
