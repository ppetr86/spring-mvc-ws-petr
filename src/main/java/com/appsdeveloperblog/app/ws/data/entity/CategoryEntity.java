package com.appsdeveloperblog.app.ws.data.entity;

import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.appsdeveloperblog.app.ws.shared.Constants;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class CategoryEntity extends IdBasedTimeRevisionEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3891933895421080356L;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(length = 64, nullable = false, unique = true)
    private String alias;

    @Column(length = 128, nullable = true)
    private String image;

    private boolean enabled;

    @Transient
    private boolean hasChildren;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;

    @OneToMany(mappedBy = "parent")
    @OrderBy("name asc")
    private Set<CategoryEntity> children = new HashSet<>();


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

    @Transient
    public String getImagePath() {

        if (this.id == null) return "/images/image-thumbnail.png";

        return Constants.S3_BASE_URI + "/category-images/" + this.id + "/" + this.image;
    }

    @Override
    public String toString() {
        return this.name;
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
}
