package market.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {

    @ManyToMany(mappedBy = "roles")
    Set<Employee> employees = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Integer version;

    @Size(max = 255)
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * for JPA
     */
    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }
}
