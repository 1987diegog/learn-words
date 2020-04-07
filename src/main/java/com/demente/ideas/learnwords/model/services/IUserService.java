package com.demente.ideas.learnwords.model.services;

import com.demente.ideas.learnwords.exceptions.BussinesServiceException;
import com.demente.ideas.learnwords.exceptions.UserNotFoundException;
import com.demente.ideas.learnwords.model.entity.User;

import java.util.List;

public interface IUserService {

    User getMockUser();

    User save(User User) throws BussinesServiceException;

    User update(User user) throws UserNotFoundException, BussinesServiceException;

    List<User> findAll() throws BussinesServiceException;

    User findById(Long id) throws UserNotFoundException, BussinesServiceException;

    User findByEmail(String email) throws UserNotFoundException, BussinesServiceException;

    void delete(User user) throws BussinesServiceException;
}
