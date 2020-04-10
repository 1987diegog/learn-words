package com.demente.ideas.learnwords;

import com.demente.ideas.learnwords.dtos.WordDTO;
import com.demente.ideas.learnwords.mapper.WordMapper;
import com.demente.ideas.learnwords.model.entity.Word;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class LearnWordsApplicationTests {

//	@Test
//	void contextLoads() {
//	}

    private WordMapper mapper
            = Mappers.getMapper(WordMapper.class);

    @Test
    public void givenWordFromWordDTO() {

        WordDTO dto = new WordDTO();
        dto.setId(1L);
        dto.setWord("Hola");

        Word entity = mapper.wordDTOtoWordEntity(dto);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getWord(), entity.getWord());
    }


    @Test
    public void givenWordDTOFromWord() {

        Word entity = new Word();
        entity.setId(1L);
        entity.setWord("Hola");


        WordDTO dto = mapper.wordEntityToWordDTO(entity);

        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getWord(), entity.getWord());
    }
}
