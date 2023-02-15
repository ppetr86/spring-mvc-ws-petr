package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;


@Entity
@Table(name = "authorities")
@Getter
@Setter
public class AuthorityEntity extends IdBasedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -5828101164006114538L;

    @Column(nullable = false, length = 20)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Collection<RoleEntity> roles;

    public AuthorityEntity(String name) {
        this.name = name;
    }

    public AuthorityEntity() {
        super();
    }

    protected AuthorityEntity(UUID id) {
        super(id);
    }

    public AuthorityEntity(UUID id, String name, Collection<RoleEntity> roles) {
        super(id);
        this.name = name;
        this.roles = roles;
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
