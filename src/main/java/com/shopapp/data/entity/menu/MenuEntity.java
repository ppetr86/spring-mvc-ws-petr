package com.shopapp.data.entity.menu;

import com.shopapp.data.entity.article.ArticleEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "menus")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MenuEntity extends IdBasedEntity implements Serializable {

    @Enumerated(EnumType.ORDINAL)
    private MenuTypeEntity type;

    @Column(nullable = false, length = 128, unique = true)
    private String title;

    @Column(nullable = false, length = 256, unique = true)
    private String alias;

    private int position;

    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MenuEntity other = (MenuEntity) obj;
        if (id == null) {
            return other.id == null;
        } else return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", type=" + type + ", title=" + title + ", position=" + position + "]";
    }
}
