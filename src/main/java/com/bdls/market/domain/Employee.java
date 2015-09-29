package com.bdls.market.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonSerialize
public class Employee {

    @ManyToMany
    @JoinTable(
            name = "employee_role",
            joinColumns = {@JoinColumn(name = "employee_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    private Set<Gallery> roles = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;
    @Version
    private Integer version;
    @Size(max = 255)
    @Column(nullable = false, unique = true)
    private String username;
    @Size(max = 255)
    @Column(nullable = false)
    private String password;

    /**
     * for JPA
     */
    public Employee() {
        username = null;
        password = null;
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Set<Gallery> getRoles() {
        return roles;
    }

    public Long getId() {
        return id;
    }

    public Integer getVersion() {
        return version;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
