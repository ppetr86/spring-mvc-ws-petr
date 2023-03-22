package com.shopapp.security;

import com.shopapp.data.entity.AuthorityEntity;
import com.shopapp.data.entity.RoleEntity;
import com.shopapp.data.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

public class UserPrincipal implements UserDetails {

    @Serial
    private static final long serialVersionUID = -7530187709860249942L;

    private final UserEntity user;

    public UserPrincipal(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<AuthorityEntity> authorityEntities = new HashSet<>();

        Collection<RoleEntity> roles = Optional.ofNullable(user)
                .map(UserEntity::getRoles)
                .orElse(Collections.emptySet());

        if (roles.isEmpty())
            return authorities;

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
        return user.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
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
        return this.user.isVerified();
    }

}
