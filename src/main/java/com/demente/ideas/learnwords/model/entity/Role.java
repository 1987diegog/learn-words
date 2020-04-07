package com.demente.ideas.learnwords.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    @NotNull
    private RoleName authority;

    public Role() {
    }

    public Role(RoleName authority) {
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getAuthority() {
        return authority;
    }

    public void setAuthority(RoleName authority) {
        this.authority = authority;
    }
}