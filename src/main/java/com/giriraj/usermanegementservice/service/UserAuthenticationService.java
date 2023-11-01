package com.giriraj.usermanegementservice.service;

import com.giriraj.usermanegementservice.exception.InvalidPasswordException;
import com.giriraj.usermanegementservice.exception.InvalidTokenException;
import com.giriraj.usermanegementservice.exception.UserNotFoundException;
import com.giriraj.usermanegementservice.model.Session;

public interface UserAuthenticationService {
    Session login(Long id, String email, String password) throws UserNotFoundException, InvalidPasswordException;

    String logout(String token) throws InvalidTokenException;

    String validateToken(String token) throws InvalidTokenException;
}
