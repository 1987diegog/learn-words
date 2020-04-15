package com.demente.ideas.learnwords.mapper;

import com.demente.ideas.learnwords.dtos.WordDTO;
import com.demente.ideas.learnwords.model.domain.entity.Meaning;
import com.demente.ideas.learnwords.model.domain.entity.Tag;
import com.demente.ideas.learnwords.model.domain.entity.Word;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
class WordMapperTests {

    ///////////////////////////////////////////////////////
    ///////////////////// TEST MAPPER /////////////////////
    ///////////////////////////////////////////////////////

    @Autowired
    private WordMapper wordMapper;

    @Test
    public void givenWordDTOFromWord() {

        Word word = new Word("Functional interface");
        word.setId(1L);

        Tag tag = new Tag("Sistemas");
        tag.setId(1L);
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);

        Meaning meaning = new Meaning("Se le conoce como interface funcional a toda aquella interface que tenga " +
                "solamente un método abstracto, es decir puede implementar uno o más métodos default, pero deberá " +
                "tener forzosamente un único método abstracto. Si no te queda claro que es un método abstracto en una " +
                "interface, es un método sin implementar.");
        meaning.setId(1L);
        meaning.setWord(word);

        Set<Meaning> meanings = new HashSet<>();
        meanings.add(meaning);

        word.setTags(tags);
        word.setMeanings(meanings);

        WordDTO dto = wordMapper.create(word);

        assertEquals(dto.getId(), word.getId());
        assertEquals(dto.getWord(), word.getWord());
    }

    @Test
    public void givenWordFromWordDTO() {

        WordDTO dto = new WordDTO();
        dto.setId(1L);
        dto.setWord("Hola");

        Word entity = wordMapper.create(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getWord(), entity.getWord());
    }
}
