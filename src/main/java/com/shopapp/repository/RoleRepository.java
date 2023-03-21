package com.shopapp.repository;

import com.shopapp.data.entity.RoleEntity;
import com.shopapp.shared.Roles;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends IdBasedRepository<RoleEntity> {
    RoleEntity findByName(Roles name);
}
