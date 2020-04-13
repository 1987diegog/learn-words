package com.demente.ideas.learnwords.dtos;

import java.io.Serializable;

/**
 * @author 1987diegog
 */
public class TagDTO implements Serializable {

    private Long id;
    public String name;

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
