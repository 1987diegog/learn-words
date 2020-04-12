package com.demente.ideas.learnwords.mapper;

import com.demente.ideas.learnwords.dtos.UsersListDTO;
import com.demente.ideas.learnwords.dtos.UserDTO;
import com.demente.ideas.learnwords.model.domain.UserList;
import com.demente.ideas.learnwords.model.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "idUser", source = "entity.id"),
    })
    UserDTO create(User entity);

    @Mappings({
            @Mapping(target = "id", source = "dto.idUser"),
    })
    User create(UserDTO dto);

    UsersListDTO create(UserList userList);
}