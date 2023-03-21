package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.shopapp.shared.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "brands")
@Getter
@Setter
public class BrandEntity extends IdBasedTimeRevisionEntity implements Serializable{

	@Serial
	private static final long serialVersionUID = -4280261731794140574L;

	@Column(nullable = false, length = 45, unique = true)
	private String name;

	@Column(nullable = false, length = 128)
	private String logo;

	@ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(
			name = "brands_categories",
			joinColumns = @JoinColumn(name = "brand_id"),
			inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<CategoryEntity> categories = new HashSet<>();

	public BrandEntity() {
		super();
	}
	
	public BrandEntity(UUID id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Brand [id=" + id + ", name=" + name + ", categories=" + categories + "]";
	}
	
	@Transient
	public String getLogoPath() {
		if (this.id == null) return "/images/image-thumbnail.png";

		return Constants.S3_BASE_URI + "/brand-logos/" + this.id + "/" + this.logo;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BrandEntity that)) return false;
		if (!super.equals(o)) return false;
		return super.equalsId(o) || name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return hashCodeId() + Objects.hash(name);
	}

	public void addCategory(CategoryEntity value) {
		if (value != null && !this.categories.contains(value)) {
			this.categories.add(value);
			value.getBrands().add(this);
		}
	}

	public void removeCategory(CategoryEntity value) {
		if (value != null && this.categories.contains(value)) {
			this.categories.remove(value);
			value.getBrands().remove(this);
		}
	}
}
