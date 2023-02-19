package com.appsdeveloperblog.app.ws.data.entity;


import com.appsdeveloperblog.app.ws.data.entity.superclass.IdBasedTimeRevisionEntity;
import com.appsdeveloperblog.app.ws.shared.Constants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
public class ProductEntity extends IdBasedTimeRevisionEntity implements Serializable {

    @Column(unique = true, length = 255, nullable = false)
    private String name;

    @Column(unique = true, length = 255, nullable = false)
    private String alias;

    @Column(length = 512, nullable = false, name = "short_description")
    private String shortDescription;

    @Column(length = 4096, nullable = false, name = "full_description")
    private String fullDescription;

    private boolean enabled;

    @Column(name = "in_stock")
    private boolean inStock;

    // Generate automatically column name as "cost"
    private float cost;

    // Generate automatically column name as "price"
    private float price;

    @Column(name = "discount_percent")
    private float discountPercent;

    private float length;
    private float width;
    private float height;
    private float weight;

    private int reviewCount;
    private float averageRating;

    @Column(name = "main_image", nullable = false)
    private String mainImage;

    @ManyToOne
    @JoinColumn(name = "category")
    private CategoryEntity category;

    @ManyToOne
    @JoinColumn(name = "brand")
    private BrandEntity brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImageEntity> images = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetailEntity> details = new ArrayList<>();
    @Transient
    private boolean customerCanReview;
    @Transient
    private boolean reviewedByCustomer;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductEntity that)) return false;
        if (!super.equals(o)) return false;
        return super.equalsId(o) || name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return hashCodeId() + Objects.hash(name);
    }

    public void addDetail(String name, String value) {
        this.details.add(new ProductDetailEntity(name, value, this));
    }

    public void addDetail(UUID id, String name, String value) {
        this.details.add(new ProductDetailEntity(id, name, value, this));
    }

    public void addExtraImage(String imageName) {
        this.images.add(new ProductImageEntity(imageName, this));
    }

    public void addImage(ProductImageEntity value) {
        if (value != null&& !this.images.contains(value)) {
            this.images.add(value);
            value.setProduct(this);
        }
    }

    public void addProductDetails(ProductDetailEntity value) {
        if (value != null && !this.details.contains(value)) {
            this.details.add(value);
            value.setProduct(this);
        }
    }

    public boolean containsImageName(String imageName) {

        for (ProductImageEntity image : images) {
            if (image.getName().equals(imageName)) {
                return true;
            }
        }

        return false;
    }

    @Transient
    public float getDiscountPrice() {
        if (discountPercent > 0) {
            return price * ((100 - discountPercent) / 100);
        }
        return this.price;
    }

    @Transient
    public String getMainImagePath() {
        if (id == null || mainImage == null) return "/images/image-thumbnail.png";

        return Constants.S3_BASE_URI + "/product-images/" + this.id + "/" + this.mainImage;
    }

    @Transient
    public String getShortName() {
        if (name.length() > 70) {
            return name.substring(0, 70).concat("...");
        }
        return name;
    }

    @Transient
    public String getURI() {
        return "/p/" + this.alias + "/";
    }

    public void removeImage(ProductImageEntity value) {
        if (value != null && this.images.contains(value)) {
            this.images.remove(value);
            value.setProduct(null);
        }
    }

    public void removeProductDetails(ProductDetailEntity value) {
        if (value != null && this.details.contains(value)) {
            this.details.remove(value);
            value.setProduct(null);
        }
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + "]";
    }

}
