package com.shopapp.data.entity.snapshots;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "categories_snapshots")
@NoArgsConstructor
@Getter
@Setter
public class CategorySnapshotEntity extends IdBasedTimeSnapshotEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3891933895421080356L;

    @Column(length = 128, nullable = false)
    private String name;

    @Column(length = 64, nullable = false)
    private String alias;

    @Column(length = 128, nullable = false)
    private String image;

    private boolean enabled;

    public CategorySnapshotEntity(String name) {
        this.name = name;
        this.alias = name;
        this.image = "default.png";
    }

    public CategorySnapshotEntity(UUID id, String name, String alias) {
        super();
        this.id = id;
        this.name = name;
        this.alias = alias;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static CategorySnapshotEntity copyFull(CategorySnapshotEntity category) {
        CategorySnapshotEntity copyCategory = new CategorySnapshotEntity();
        copyCategory.setId(category.getId());
        copyCategory.setName(category.getName());
        copyCategory.setImage(category.getImage());
        copyCategory.setAlias(category.getAlias());
        copyCategory.setEnabled(category.isEnabled());
        return copyCategory;
    }

    public static CategorySnapshotEntity copyFull(CategorySnapshotEntity category, String name) {
        CategorySnapshotEntity copyCategory = CategorySnapshotEntity.copyFull(category);
        copyCategory.setName(name);

        return copyCategory;
    }

    public static CategorySnapshotEntity copyIdAndName(CategorySnapshotEntity category) {
        CategorySnapshotEntity copyCategory = new CategorySnapshotEntity();
        copyCategory.setId(category.getId());
        copyCategory.setName(category.getName());

        return copyCategory;
    }

    public static CategorySnapshotEntity copyIdAndName(UUID id, String name) {
        CategorySnapshotEntity copyCategory = new CategorySnapshotEntity();
        copyCategory.setId(id);
        copyCategory.setName(name);

        return copyCategory;
    }
}
