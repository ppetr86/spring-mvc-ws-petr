package com.shopapp.data.entity.section;

import com.shopapp.data.entity.BrandEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sections_brands")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BrandSection extends IdBasedEntity {

    @Column(name = "brand_order")
    private int brandOrder;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

}
