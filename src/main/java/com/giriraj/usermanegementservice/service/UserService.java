package com.giriraj.usermanegementservice.service;

import com.giriraj.usermanegementservice.exception.EmailAlreadyExists;
import com.giriraj.usermanegementservice.exception.InvalidTokenException;
import com.giriraj.usermanegementservice.exception.UserNotFoundException;
import com.giriraj.usermanegementservice.model.Users;

public interface UserService {
    Users createUser(Users user) throws EmailAlreadyExists;
    Users getUserById(Long id) throws UserNotFoundException;

    Users getUserByEmail(String email) throws UserNotFoundException;

    String deleteUserById(Long id, String token) throws InvalidTokenException, UserNotFoundException;

    Users updateUserById(String token, Long id, Users users) throws InvalidTokenException, UserNotFoundException;
}
