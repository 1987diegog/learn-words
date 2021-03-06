package com.demente.ideas.learnwords.dtos;

import java.io.Serializable;

/**
 * @author 1987diegog
 */
public class WordDTO implements Serializable {

    private Long id;
    private String word;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
