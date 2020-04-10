package com.demente.ideas.learnwords.mapper;

import com.demente.ideas.learnwords.dtos.WordDTO;
import com.demente.ideas.learnwords.model.entity.Word;
import org.mapstruct.Mapper;

@Mapper
public interface WordMapper {
    WordDTO wordEntityToWordDTO(Word entity);
    Word wordDTOtoWordEntity(WordDTO dto);
}