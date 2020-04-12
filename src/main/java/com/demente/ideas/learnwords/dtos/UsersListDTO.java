package com.demente.ideas.learnwords.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 1987diegog
 */
public class UsersListDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<UserDTO> users;

    public UsersListDTO() {
    }

    public UsersListDTO(List<UserDTO> usersListDTO) {
        this.users = new ArrayList<>();
        this.users.addAll(usersListDTO);
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}