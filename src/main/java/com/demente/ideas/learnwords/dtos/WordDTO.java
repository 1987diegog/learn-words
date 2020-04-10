package com.demente.ideas.learnwords.dtos;

import com.demente.ideas.learnwords.model.domain.DateAudit;

import java.io.Serializable;

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
