package com.shopapp.data.entity.section;

import com.shopapp.data.entity.product.ProductEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sections_products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductSection extends IdBasedEntity {

	@Column(name = "product_order")
	private int productOrder;

	@ManyToOne
	@JoinColumn(name = "product_id")	
	private ProductEntity product;

}
