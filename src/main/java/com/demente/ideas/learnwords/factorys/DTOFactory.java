package com.demente.ideas.learnwords.factorys;

import com.demente.ideas.learnwords.dtos.UsersListDTO;
import com.demente.ideas.learnwords.dtos.UserDTO;
import com.demente.ideas.learnwords.mapper.UserMapper;
import com.demente.ideas.learnwords.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 1987diegog
 */
public class DTOFactory {

    static Logger logger = LogManager.getLogger(DTOFactory.class);

    private static UserMapper userMapper
            = Mappers.getMapper(UserMapper.class);

    /////////////////////////////////////////////////////////////
    /////////////////////////// USER ////////////////////////////
    /////////////////////////////////////////////////////////////

    /**
     * @param listUsers
     * @return
     */
    public static UsersListDTO createList(List<User> listUsers) {

        List<UserDTO> listUsersDTO = null;
        if (listUsers != null) {

            listUsersDTO = listUsers.stream().map(userMapper::create) //
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        logger.info("[GET_LIST_USERS_DTO] DTOFactory Successful");

        return new UsersListDTO(listUsersDTO);
    }
}
