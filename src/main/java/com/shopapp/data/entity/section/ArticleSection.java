package com.shopapp.data.entity.section;

import com.shopapp.data.entity.article.ArticleEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sections_articles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleSection extends IdBasedEntity {

    @Column(name = "article_order")
    private int articleOrder;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;

}
