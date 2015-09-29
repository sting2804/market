package market.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * галерея изображений товаров
 */
@Entity
public class Gallery {

    /**
     * список продуктов, к которым относится данное изображение
     */
    @ManyToMany(mappedBy = "galleries")
    private Set<Product> products = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Integer version;
    /**
     * относительная ссылка на изображение. Максимум 1000 символов
     */
    @Size(max = 1000)
    @Column(nullable = false)
    private String url;
    /**
     * краткое описание на случай неудачной загрузки картинки
     */
    @Size(max = 255)
    private String description;

    /**
     * для JPA
     */
    public Gallery() {
        url = null;
        description = null;
    }

    public Gallery(String url, String description) {
        this.url = url;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }
}
