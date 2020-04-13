package com.demente.ideas.learnwords.mapper;

import com.demente.ideas.learnwords.dtos.WordDTO;
import com.demente.ideas.learnwords.model.domain.entity.Word;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

/**
 * @author 1987diegog
 */
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface WordMapper {
    WordDTO create(Word entity);
    Word create(WordDTO dto);
}