package market.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Описание продуктов
 */
@Entity
public class Product {

    @ManyToMany
    @JoinTable(
            name = "product_gallery",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "gallery_id", referencedColumnName = "id")}
    )
    private Set<Gallery> galleries = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = {@JoinColumn(name = "product_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id", referencedColumnName = "id")}
    )
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "product")
    private List<Review> reviews = new ArrayList<>();

    @Id
    @GeneratedValue
    private Long code;
    @Version
    private Integer version;

    /**
     * наименование товара
     */
    @Size(max = 255)
    @NotNull
    private String name;
    /**
     * описание товара
     */
    @Size(max = 1000)
    @NotNull
    private String description;
    private Integer number;
    private boolean isAvailable;

    /**
     * для JPA
     */
    public Product() {
        name = null;
        description = null;
        number = 0;
        isAvailable = false;
    }

    public Product(String name, String description, Integer number, boolean isAvailable) {
        this.name = name;
        this.description = description;
        this.number = number;
        this.isAvailable = isAvailable;
    }

    public Set<Gallery> getGalleries() {
        return galleries;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Long getCode() {
        return code;
    }

    public Integer getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getNumber() {
        return number;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}
