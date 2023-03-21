package com.shopapp.security;

import com.shopapp.data.entity.AuthorityEntity;
import com.shopapp.data.entity.CustomerEntity;
import com.shopapp.data.entity.RoleEntity;
import com.shopapp.data.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;

public class UserPrincipal implements UserDetails {

    @Serial
    private static final long serialVersionUID = -7530187709860249942L;

    private final UserEntity userEntity;
    private String userId;

    private final CustomerEntity customerEntity;
    private final String customerId;

    public UserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.userId = userEntity.getId().toString();

        this.customerId = null;
        this.customerEntity=null;
    }

    public UserPrincipal(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
        this.customerId = customerEntity.getId().toString();

        this.userEntity = null;
        this.userId=null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<AuthorityEntity> authorityEntities = new HashSet<>();

        //TODO: fix so that it works for both user and customer
        // Get user Roles
        Collection<RoleEntity> roles = userEntity.getRoles();

        if (roles == null) return authorities;

        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
            authorityEntities.addAll(role.getAuthorities());
        });

        authorityEntities.forEach((authorityEntity) -> {
            authorities.add(new SimpleGrantedAuthority(authorityEntity.getName().name()));
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userEntity.getEncryptedPassword();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return this.userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userEntity.isVerified();
    }

}
