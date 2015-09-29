package com.bdls.market.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Отзывы о товарах
 */
@Entity
public class Review {

    /**
     * ссылка на родительский комментарий
     */
    @ManyToOne
    private Review parentReview;
    /**
     * список дочерних комментариев
     */
    @OneToMany(mappedBy = "parentReview")
    private List<Review> subsidiaryReviews = new ArrayList<>();

    /**
     * ссылка на продукт, к которому относится коммент
     */
    @ManyToOne
    private Product product;

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Integer version;

    /**
     * текст комментария, не больше 1000 символов
     */
    @Column(nullable = false)
    @Size(max = 1000)
    private String text;

    /**
     * для JPA
     */
    public Review() {
        text = null;
    }

    public Review(Review parentReview, Product product, String text) {
        this.parentReview = parentReview;
        this.product = product;
        this.text = text;
    }

    public Review getParentReview() {
        return parentReview;
    }

    public List<Review> getSubsidiaryReviews() {
        return subsidiaryReviews;
    }

    public Product getProduct() {
        return product;
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getText() {
        return text;
    }
}
