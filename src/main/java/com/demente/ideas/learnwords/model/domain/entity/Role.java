package com.demente.ideas.learnwords.model.domain.entity;

import com.demente.ideas.learnwords.model.domain.RoleName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author 1987diegog
 */
@Entity
@Table(name = "DEMENTE_T_ROLES")
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