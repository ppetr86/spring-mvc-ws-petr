package com.shopapp.data.entity.section;

import com.shopapp.data.entity.CategoryEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sections_categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategorySection extends IdBasedEntity {

    @Column(name = "category_order")
    private int categoryOrder;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

}