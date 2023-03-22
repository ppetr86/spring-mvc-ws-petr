package com.shopapp.data.entity.article;

import com.shopapp.data.entity.UserEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "articles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleEntity extends IdBasedEntity implements Serializable {

    @Column(nullable = false, length = 256)
    private String title;

    @Column(nullable = false)
    @Lob
    private String content;

    @Column(nullable = false, length = 500)
    private String alias;

    @Enumerated(EnumType.ORDINAL)
    private ArticleType type;

    @Column(name = "updated_time")
    private Date updatedTime;

    private boolean published;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ArticleEntity(UUID id) {
        this.id = id;
    }

    public ArticleEntity(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public ArticleEntity(UUID id, String title, ArticleType type, Date updatedTime, boolean published, UserEntity user) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.updatedTime = updatedTime;
        this.published = published;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Article [title=" + title + ", type=" + type + "]";
    }
}
