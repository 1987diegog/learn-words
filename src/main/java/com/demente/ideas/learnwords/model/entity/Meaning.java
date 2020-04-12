package com.demente.ideas.learnwords.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "IDDE_T_MEANINGS")
public class Meaning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_WORD", foreignKey = @ForeignKey(name = "FK_WORD"))
    private Word word;

    public Meaning() {
    }

    public Meaning(String meaning) {
        this.description = meaning;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
