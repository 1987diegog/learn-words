package com.demente.ideas.learnwords.view;

import com.demente.ideas.learnwords.dtos.UserDTO;
import com.demente.ideas.learnwords.dtos.UsersListDTO;
import com.demente.ideas.learnwords.exceptions.InternalServerErrorException;
import com.demente.ideas.learnwords.exceptions.NotFoundException;
import com.demente.ideas.learnwords.exceptions.UserNotFoundException;
import com.demente.ideas.learnwords.mapper.UserMapper;
import com.demente.ideas.learnwords.model.domain.Status;
import com.demente.ideas.learnwords.model.domain.UserList;
import com.demente.ideas.learnwords.model.domain.entity.User;
import com.demente.ideas.learnwords.model.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 1987diegog
 */
@RestController
@RequestMapping("/api/v1/users")
@Api(tags = "User")
public class UserRestController {

    private Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public UserRestController() {
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(//
            consumes = {MediaType.APPLICATION_JSON_VALUE}, //
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Create a user", //
            notes = "Service to create a user")
    @ApiResponses(value = { //
            @ApiResponse(code = 201, message = "User created successfully"), //
            @ApiResponse(code = 500, message = "Internal system error")})
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO)
            throws InternalServerErrorException {
        try {
            logger.info("[CREATE_USER] - It will try to create the user with email: " + userDTO.getEmail());
            User user = userService.save(userMapper.create(userDTO));
            UserDTO userResponseDTO = userMapper.create(user);
            logger.info("[CREATE_USER] - User created successful, the associated id was: " + user.getId()
                    + ", email: " + userDTO.getEmail());
            return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("[CREATE_USER] [ERROR] - Internal server error, when trying to create user with email: "
                    + userDTO.getEmail(), e);
            throw new InternalServerErrorException("Internal server error, when trying to create user with email: " +
                    userDTO.getEmail());
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Update user data", //
            notes = "Service for update user data")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "User updated successfully"), //
            @ApiResponse(code = 404, message = "User not found"), //
            @ApiResponse(code = 500, message = "Internal system error")})
    public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO)
            throws NotFoundException, InternalServerErrorException {
        try {
            logger.info("[UPDATE_USER] - It will try to update the user with id: " + userDTO.getIdUser());
            User user = userService.findById(userDTO.getIdUser());

            // modify user data
            if (user != null && userDTO != null) {
                user.setName(userDTO.getName());
                user.setUsername(userDTO.getUsername());
                user.setEmail(userDTO.getEmail());
                user.setStatus(Status.get(userDTO.getStatus()));
            }

            User userModified = userService.update(user);
            UserDTO userResponseDTO = userMapper.create(userModified);
            logger.info("[UPDATE_USER] - User updated successful, id: " + userDTO.getIdUser());
            return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.info("[UPDATE_USER] [NOT_FOUND] - User not found, id: " + userDTO.getIdUser());
            throw new NotFoundException("User not found, id: " + userDTO.getIdUser());
        } catch (Exception e) {
            logger.error("[UPDATE_USER] [ERROR] - Internal server error, when trying to update user data with id: "
                    + userDTO.getIdUser(), e);
            throw new InternalServerErrorException("Internal server error, when trying to update user data with id: "
                    + userDTO.getIdUser());
        }
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete a user", //
            notes = "Service to delete a user")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "User deleted successful"), //
            @ApiResponse(code = 404, message = "User not found"), //
            @ApiResponse(code = 500, message = "Internal system error")})
    public ResponseEntity<String> delete(@PathVariable("id") Long id)
            throws NotFoundException, InternalServerErrorException {
        try {
            logger.info("[DELETE_USER] - It will try to delete user with id: " + id);
            User user = userService.findById(id);
            this.userService.delete(user);
            logger.info("[DELETE_USER] - User was deleted successful, id: " + id);
            return new ResponseEntity<>("User was deleted successful, id:" + id, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.info("[DELETE_USER] [NOT_FOUND] - User not found, id: " + id);
            throw new NotFoundException("User not found, id: " + id);
        } catch (Exception e) {
            logger.error("[DELETE_USER] [ERROR] - Internal system error when trying to delete user whit id: " + id, e);
            throw new InternalServerErrorException("Internal system error when trying to delete user whit id: " + id);
        }
    }

    //////////////////////////////////////////////////////////////////
    ///////////////////////////// QUERIES ////////////////////////////
    /////////////////////////////////////////////////////////////////

    @Secured("ROLE_USER")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Returns all users", //
            notes = "Service returns all users")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Users found"), //
            @ApiResponse(code = 500, message = "Internal system error")})
    public ResponseEntity<UsersListDTO> findAll()
            throws InternalServerErrorException {
        try {
            logger.info("[GET_ALL_USERS] - It will try to return all system users...");
            UserList userList = this.userService.findAll();
            UsersListDTO usersListDTO = userMapper.create(userList);
            logger.info("[GET_ALL_USERS] - Get all users finished successfully ");
            return new ResponseEntity<>(usersListDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("[GET_ALL_USERS] [ERROR] - Internal system error, when trying to get all users", e);
            throw new InternalServerErrorException("Internal server error, when trying to get all users");
        }
    }

    @Secured("ROLE_USER")
    @GetMapping(path = "/{id}", //
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "Get a user by id", //
            notes = "Service to returns a user by id")
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "User found"), //
            @ApiResponse(code = 404, message = "User not found"), //
            @ApiResponse(code = 500, message = "Internal system error")})
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id)
            throws NotFoundException, InternalServerErrorException {
        try {
            logger.info("[FIND_BY_USER_ID] - It will try to return a user by id: " + id);
            User user = userService.findById(id);
            UserDTO userDTO = userMapper.create(user);
            logger.info("[FIND_BY_USER_ID] - User found successful, id: " + id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            logger.info("[FIND_BY_USER_ID] [NOT_FOUND] - User not found, id: " + id);
            throw new NotFoundException("User not found, id: " + id);
        } catch (Exception e) {
            logger.error("[FIND_BY_USER_ID] [ERROR] - To try get user with id: " + id, e);
            throw new InternalServerErrorException("Internal server error, when trying to get user with id: " + id);
        }
    }
}
