package com.appsdeveloperblog.app.ws.io.entity.product;


import com.appsdeveloperblog.app.ws.io.entity.Brand;
import com.appsdeveloperblog.app.ws.io.entity.Category;
import com.appsdeveloperblog.app.ws.io.entity.superclass.CreateAndUpdateTimeIdBasedEntity;
import com.appsdeveloperblog.app.ws.shared.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@Getter
@Setter
public class Product extends CreateAndUpdateTimeIdBasedEntity implements Serializable {

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
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductImage> images = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDetail> details = new ArrayList<>();

    @Transient
    private boolean customerCanReview;

    @Transient
    private boolean reviewedByCustomer;

    public Product(UUID id) {
        this.id = id;
    }

    public Product(String name) {
        this.name = name;
    }

    public void addExtraImage(String imageName) {
        this.images.add(new ProductImage(imageName, this));
    }

    public void addDetail(String name, String value) {
        this.details.add(new ProductDetail(name, value, this));
    }

    @Transient
    public String getMainImagePath() {
        if (id == null || mainImage == null) return "/images/image-thumbnail.png";

        return Constants.S3_BASE_URI + "/product-images/" + this.id + "/" + this.mainImage;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + "]";
    }

    public void addDetail(UUID id, String name, String value) {
        this.details.add(new ProductDetail(id, name, value, this));
    }

    public boolean containsImageName(String imageName) {
        Iterator<ProductImage> iterator = images.iterator();

        while (iterator.hasNext()) {
            ProductImage image = iterator.next();
            if (image.getName().equals(imageName)) {
                return true;
            }
        }

        return false;
    }

    @Transient
    public String getShortName() {
        if (name.length() > 70) {
            return name.substring(0, 70).concat("...");
        }
        return name;
    }

    @Transient
    public float getDiscountPrice() {
        if (discountPercent > 0) {
            return price * ((100 - discountPercent) / 100);
        }
        return this.price;
    }

    @Transient
    public String getURI() {
        return "/p/" + this.alias + "/";
    }

}