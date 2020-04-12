package com.demente.ideas.learnwords.factorys;

import com.demente.ideas.learnwords.dtos.UserDTO;
import com.demente.ideas.learnwords.model.domain.Status;
import com.demente.ideas.learnwords.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

/**
 * @author 1987diegog
 */
public class BOFactory {

    static Logger logger = LogManager.getLogger(DTOFactory.class);

    /////////////////////////////////////////////////////////////
    ////////////////////////// USER /////////////////////////////
    /////////////////////////////////////////////////////////////

    /**
     * @param user
     * @param userDTO
     * @return
     */
    public static User modify(User user, UserDTO userDTO) {

        logger.info("[MODIFY_USER_BO] Start modify user BOFactory...");

        if (user != null && userDTO != null) {
            user.setName(userDTO.getName());
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setStatus(Status.get(userDTO.getStatus()));
        }

        logger.info("[MODIFY_USER_BO] Modify user BOFactory Successful");

        return user;
    }
}