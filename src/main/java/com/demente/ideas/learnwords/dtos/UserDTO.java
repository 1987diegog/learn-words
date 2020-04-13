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

    private String password;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Status is mandatory")
    private String status;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String lastName, String username,
                   String password, String email, String status) {

        this.idUser = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.status = status;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
