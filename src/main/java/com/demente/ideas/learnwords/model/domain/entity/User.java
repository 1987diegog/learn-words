package com.demente.ideas.learnwords.model.domain.entity;

import com.demente.ideas.learnwords.model.domain.Status;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 1987diegog
 */
@Entity
@Table(name = "DEMENTE_T_USERS", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 40)
    private String name;

    @Size(max = 40)
    private String lastName;

    @Size(max = 15)
    private String username;

    @Size(max = 100)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STATUS", length = 30)
    private Status status;

    @Size(max = 40)
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "DEMENTE_T_USER_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {

    }

    public User(Long id, String name, String lastName, String username,
                String password, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = Status.ENABLED;
        this.roles = new HashSet<>();
    }

    public boolean isEnabled() {
        if (this.status.equals(Status.ENABLED)) {
            return true;
        } else {
            return false;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}