package com.demente.ideas.learnwords.factorys;

import com.demente.ideas.learnwords.dtos.ListUsersDTO;
import com.demente.ideas.learnwords.dtos.UserDTO;
import com.demente.ideas.learnwords.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 1987diegog
 */
public class DTOFactory {

    static Logger logger = LogManager.getLogger(DTOFactory.class);

    /////////////////////////////////////////////////////////////
    /////////////////////////// USER ////////////////////////////
    /////////////////////////////////////////////////////////////

    /**
     * @param listUsers
     * @return
     */
    public static ListUsersDTO createList(List<User> listUsers) {

        List<UserDTO> listUsersDTO = null;
        if (listUsers != null) {

            listUsersDTO = listUsers.stream().map(DTOFactory::create) //
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        logger.info("[GET_LIST_USERS_DTO] DTOFactory Successful");

        return new ListUsersDTO(listUsersDTO);
    }


    /**
     * @param user
     * @return
     */
    public static UserDTO create(User user) {

        UserDTO userDTO = null;
        if (user != null) {
            userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);

            //////////////////////////////////////////////////
            // Additional attributes or data types to adapt //
            //////////////////////////////////////////////////
            userDTO.setStatus(user.getStatus().name());
        }

        return userDTO;
    }
}
