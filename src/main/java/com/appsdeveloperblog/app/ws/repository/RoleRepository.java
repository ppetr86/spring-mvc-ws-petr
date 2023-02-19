package com.appsdeveloperblog.app.ws.repository;

import com.appsdeveloperblog.app.ws.data.entity.RoleEntity;
import com.appsdeveloperblog.app.ws.shared.Roles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends IdBasedRepository<RoleEntity> {
    RoleEntity findByName(Roles name);
}
