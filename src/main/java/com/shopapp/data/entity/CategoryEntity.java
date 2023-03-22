package com.shopapp.data.entity;

import com.shopapp.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.shopapp.shared.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class CategoryEntity extends IdBasedTimeRevisionEntity implements Serializable {

    @ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
    Set<BrandEntity> brands;
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
    private CategoryEntity parent;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "parent")
    @OrderBy("name asc")
    private Set<CategoryEntity> children = new HashSet<>();
    @Column(name = "all_parent_ids", length = 256, nullable = true)
    private String allParentIDs;

    public CategoryEntity(UUID id) {
        this.id = id;
    }

    public CategoryEntity(String name) {
        this.name = name;
        this.alias = name;
        this.image = "default.png";
    }

    public CategoryEntity(String name, CategoryEntity parent) {
        this(name);
        this.parent = parent;
    }

    public CategoryEntity(UUID id, String name, String alias) {
        super();
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    public static CategoryEntity copyFull(CategoryEntity category) {
        CategoryEntity copyCategory = new CategoryEntity();
        copyCategory.setId(category.getId());
        copyCategory.setName(category.getName());
        copyCategory.setImage(category.getImage());
        copyCategory.setAlias(category.getAlias());
        copyCategory.setEnabled(category.isEnabled());
        copyCategory.setHasChildren(category.getChildren().size() > 0);
        return copyCategory;
    }

    public static CategoryEntity copyFull(CategoryEntity category, String name) {
        CategoryEntity copyCategory = CategoryEntity.copyFull(category);
        copyCategory.setName(name);

        return copyCategory;
    }

    public static CategoryEntity copyIdAndName(CategoryEntity category) {
        CategoryEntity copyCategory = new CategoryEntity();
        copyCategory.setId(category.getId());
        copyCategory.setName(category.getName());

        return copyCategory;
    }

    public static CategoryEntity copyIdAndName(UUID id, String name) {
        CategoryEntity copyCategory = new CategoryEntity();
        copyCategory.setId(id);
        copyCategory.setName(name);

        return copyCategory;
    }

    public void addChildCategory(CategoryEntity value) {
        if (value != null && !this.children.contains(value)) {
            this.children.add(value);
            value.setParent(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryEntity that)) return false;
        if (!super.equals(o)) return false;
        return super.equalsId(o) || name.equals(that.name);
    }

    @Transient
    public String getImagePath() {

        if (this.id == null) return "/images/image-thumbnail.png";

        return Constants.S3_BASE_URI + "/category-images/" + this.id + "/" + this.image;
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(name);
    }

    public void removeChildCategory(CategoryEntity value) {
        if (value != null && this.children.contains(value)) {
            this.children.remove(value);
            value.setParent(null);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
