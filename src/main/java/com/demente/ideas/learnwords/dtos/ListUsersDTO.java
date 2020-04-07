package com.demente.ideas.learnwords.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 1987diegog
 */
public class ListUsersDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<UserDTO> users;

    public ListUsersDTO() {
    }

    public ListUsersDTO(List<UserDTO> listUsersDTO) {
        this.users = new ArrayList<>();
        this.users.addAll(listUsersDTO);
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}