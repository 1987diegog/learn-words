package com.demente.ideas.learnwords.mapper;

import com.demente.ideas.learnwords.dtos.WordDTO;
import com.demente.ideas.learnwords.model.entity.Word;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WordMapper {
    WordDTO create(Word entity);
    Word create(WordDTO dto);
}