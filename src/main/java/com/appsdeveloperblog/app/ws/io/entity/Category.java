package com.appsdeveloperblog.app.ws.io.entity;

import com.appsdeveloperblog.app.ws.io.entity.superclass.CreateAndUpdateTimeIdBasedEntity;
import com.appsdeveloperblog.app.ws.shared.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category extends CreateAndUpdateTimeIdBasedEntity implements Serializable{

	@Serial
	private static final long serialVersionUID = 3891933895421080356L;

	@Column(length = 128, nullable = false, unique = true)
	private String name;

	@Column(length = 64, nullable = false, unique = true)
	private String alias;

	@Column(length = 128, nullable = false)
	private String image;

	private boolean enabled;
	
	@Transient
	private boolean hasChildren;
	
	@OneToOne
	@JoinColumn(name = "parent_id")
	private Category parent;

	@OneToMany(mappedBy = "parent")
	@OrderBy("name asc")
	private Set<Category> children = new HashSet<>();
	
	@Column(name = "all_parent_ids", length = 256, nullable = true)
	private String allParentIDs;
	

	public Category(String name) {
		this.name = name;
		this.alias = name;
		this.image = "default.png";
	}

	public Category(String name, Category parent) {
		this(name);
		this.parent = parent;
	}
	
	public Category(UUID id, String name, String alias) {
		super();
		this.id = id;
		this.name = name;
		this.alias = alias;
	}
	
	public static Category copyIdAndName(Category category) {
		Category copyCategory = new Category();
		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());

		return copyCategory;
	}

	public static Category copyIdAndName(UUID id, String name) {
		Category copyCategory = new Category();
		copyCategory.setId(id);
		copyCategory.setName(name);

		return copyCategory;
	}
	
	public static Category copyFull(Category category) {
		Category copyCategory = new Category();
		copyCategory.setId(category.getId());
		copyCategory.setName(category.getName());
		copyCategory.setImage(category.getImage());
		copyCategory.setAlias(category.getAlias());
		copyCategory.setEnabled(category.isEnabled());
		copyCategory.setHasChildren(category.getChildren().size() > 0);
		return copyCategory;		
	}

	public static Category copyFull(Category category, String name) {
		Category copyCategory = Category.copyFull(category);
		copyCategory.setName(name);

		return copyCategory;
	}
	
	@Transient
	public String getImagePath() {
		
		if (this.id == null) return "/images/image-thumbnail.png";
		
		return Constants.S3_BASE_URI + "/category-images/" + this.id + "/" + this.image;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
