package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedEntity;
import com.appsdeveloperblog.app.ws.shared.Authority;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "authorities")
@Getter
@Setter
public class AuthorityEntity extends IdBasedEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -5828101164006114538L;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private Authority name;

    //TODO: consider different approach to EAGER...
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    private Set<RoleEntity> roles = new HashSet<>();


    public AuthorityEntity() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorityEntity that)) return false;
        return super.equalsId(o) || name == that.name && roles.equals(that.roles);
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(name);
    }

}
