package com.shopapp.repository;

import com.shopapp.data.entity.section.Section;

import java.util.List;

public interface SectionRepository extends IdBasedRepository<Section> {
    // list sections by enabled status and sorted by order in ascending order
    List<Section> findAllByEnabledOrderBySectionOrderAsc(boolean enabled);
}
