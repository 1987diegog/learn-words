package com.demente.ideas.learnwords.model.domain.entity;

import javax.persistence.*;

/**
 * @author 1987diegog
 */
@Entity
@Table(name = "DEMENTE_T_TAGS")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String name;

    public Tag() {
    }

    public Tag(String tag) {
        this.name = tag;
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
}
