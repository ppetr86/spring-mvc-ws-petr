package com.shopapp.repository;

import com.shopapp.data.entity.menu.MenuEntity;
import com.shopapp.data.entity.menu.MenuTypeEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends IdBasedRepository<MenuEntity> {

    @Query("Select m FROM MenuEntity m WHERE m.alias = ?1 AND m.enabled = true")
    MenuEntity findByAlias(String alias);

    List<MenuEntity> findByTypeAndEnabledOrderByPositionAsc(MenuTypeEntity type, boolean enabled);

}
