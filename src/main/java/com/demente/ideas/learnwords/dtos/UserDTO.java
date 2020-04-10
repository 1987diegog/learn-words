package com.demente.ideas.learnwords.dtos;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author 1987diegog
 */
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idUser;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "LastName is mandatory")
    private String lastName;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Status is mandatory")
    private String status;
    @NotBlank(message = "Email is mandatory")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}