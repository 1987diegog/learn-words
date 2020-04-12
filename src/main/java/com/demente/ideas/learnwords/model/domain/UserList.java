package com.demente.ideas.learnwords.model.domain;

import com.demente.ideas.learnwords.model.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 1987diegog
 */
public class UserList implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<User> users;

    public UserList() {
        this.users = new ArrayList<>();
    }

    public UserList(List<User> userList) {
        this.users = new ArrayList<>();
        this.users.addAll(userList);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void add(User user) {
        if (this.users != null) {
            this.users.add(user);
        }
    }
}