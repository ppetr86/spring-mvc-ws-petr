package com.shopapp.repository;

import com.shopapp.data.entity.menu.MenuEntity;
import com.shopapp.data.entity.menu.MenuTypeEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends IdBasedRepository<MenuEntity> {

	public List<MenuEntity> findByTypeAndEnabledOrderByPositionAsc(MenuTypeEntity type, boolean enabled);
	
	@Query("Select m FROM MenuEntity m WHERE m.alias = ?1 AND m.enabled = true")
	public MenuEntity findByAlias(String alias);
	
}
