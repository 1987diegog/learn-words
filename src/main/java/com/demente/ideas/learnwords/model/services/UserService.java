package com.demente.ideas.learnwords.model.services;

import com.demente.ideas.learnwords.exceptions.BussinesServiceException;
import com.demente.ideas.learnwords.exceptions.UserNotFoundException;
import com.demente.ideas.learnwords.model.domain.UserList;
import com.demente.ideas.learnwords.model.domain.entity.User;
import com.demente.ideas.learnwords.repository.IUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author 1987diegog
 */
// Service es un patron del estilo facade, nos sirve para acceder a distintos DAO
@Service
@Primary
// @Primary indica que la implementacion concreta por defecto que debe inyectar Spring
// es esta clase marcada con @Primary, esto es necesario cuando se tienen varias
// implementaciones de una determinada interface, al momento de inyectar la interface
// Spring no sabe cual inyectar a no ser que se le indique.
public class UserService implements IUserService {

    Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService() {
    }

    @Override
    public User getMockUser() {
        return new User(1L, "Diego", "Gonzalez",
                "1987diegog", "pass", "1987diegog@gmail.com");
    }

    /**
     * @param user
     * @return
     * @throws BussinesServiceException
     */
    @Transactional
    @Override
    public User save(User user) throws BussinesServiceException {
        logger.info("[USER_CREATE] - Start, creating user...");
        try {
            if (user.getPassword() != null) {
                String bCryptPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(bCryptPassword);
            }
            return this.userRepository.save(user);
        } catch (Exception e) {
            logger.error("[USER_CREATE] [ERROR] - An error occurred while trying to create a user", e);
            throw new BussinesServiceException("An error occurred while trying to create a user", e);
        }
    }

    /**
     * @param user
     * @return
     * @throws UserNotFoundException
     * @throws BussinesServiceException
     */
    @Transactional
    @Override
    public User update(User user) throws UserNotFoundException, BussinesServiceException {
        logger.info("[USER_UPDATE] - Start, modifying user...");
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("[USER_UPDATE] [ERROR] - An error occurred while trying to update a user data", e);
            throw new BussinesServiceException("An error occurred while trying to update a user data", e);
        }
    }

    /**
     * @param user
     * @throws UserNotFoundException
     * @throws BussinesServiceException
     */
    @Transactional
    @Override
    public void delete(User user) throws BussinesServiceException {
        try {
            logger.info("[USER_DELETE] - Start, removing user...");
            this.userRepository.delete(user);
        } catch (Exception e) {
            logger.error("[USER_DELETE] [ERROR] - An error occurred while trying to delete a user, " +
                    "id: " + user.getId(), e);
            throw new BussinesServiceException("An error occurred while trying to delete a user, id: " + user.getId(), e);
        }
    }

    //////////////////////////////////////////////////////////////////
    ///////////////////////////// QUERIES ////////////////////////////
    /////////////////////////////////////////////////////////////////

    // All methods that are not annotated with @Transactional
    // will be treated as a read mode transaction

    /**
     * @param id
     * @return
     * @throws UserNotFoundException
     * @throws BussinesServiceException
     */
    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) throws UserNotFoundException, BussinesServiceException {
        logger.info("[USER_FIND_BY_ID] - Start, searching user...");
        try {
            return this.userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("[USER_FIND_BY_ID] [NOT_FOUND] - User not found, id: " + id));
        } catch (UserNotFoundException e) {
            logger.info("[USER_FIND_BY_ID] [NOT_FOUND] - User not found, id: " + id);
            throw e;
        } catch (Exception e) {
            logger.error("[USER_FIND_BY_ID] [ERROR] - An error occurred while trying to find " +
                    "a user by id: " + id, e);
            throw new BussinesServiceException("An error occurred while trying to find a user by id: " + id, e);
        }
    }

    /**
     * @param email
     * @return
     * @throws UserNotFoundException
     * @throws BussinesServiceException
     */
    @Transactional(readOnly = true)
    @Override
    public User findByEmail(String email) throws UserNotFoundException, BussinesServiceException {
        logger.info("[USER_FIND_BY_EMAIL] - Start, searching user...");
        try {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User not found, email: " + email));
        } catch (UserNotFoundException e) {
            logger.info("[USER_FIND_BY_EMAIL] [NOT_FOUND] - User not found, email: " + email);
            throw e;
        } catch (Exception e) {
            logger.error("[USER_FIND_BY_EMAIL] [ERROR] - An error occurred while trying to find " +
                    "a user by email: " + email, e);
            throw new BussinesServiceException("An error occurred while trying to find a user by email: " + email, e);
        }
    }

    /**
     * @return
     * @throws BussinesServiceException
     */
    @Transactional(readOnly = true)
    @Override
    public UserList findAll() throws BussinesServiceException {
        try {
            logger.info("[USER_FIND_ALL] - Start, searching users...");
            List<User> listUsers = this.userRepository.findAll();
            if (listUsers != null) {
                logger.info("[USER_FIND_ALL] - List users size: " + listUsers.size());
            }
            return new UserList(listUsers);
        } catch (Exception e) {
            logger.error("[USER_FIND_ALL] [ERROR] - An error occurred while trying to find all users", e);
            throw new BussinesServiceException("An error occurred while trying to find all users", e);
        }
    }
}
