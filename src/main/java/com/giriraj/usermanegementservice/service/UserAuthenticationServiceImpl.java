package com.giriraj.usermanegementservice.service;

import com.giriraj.usermanegementservice.exception.InvalidPasswordException;
import com.giriraj.usermanegementservice.exception.InvalidTokenException;
import com.giriraj.usermanegementservice.exception.UserNotFoundException;
import com.giriraj.usermanegementservice.model.Session;
import com.giriraj.usermanegementservice.model.SessionStatus;
import com.giriraj.usermanegementservice.model.Users;
import com.giriraj.usermanegementservice.repository.SessionRepository;
import com.giriraj.usermanegementservice.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService{
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public UserAuthenticationServiceImpl(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;

        this.sessionRepository = sessionRepository;
    }
    @Override
    public Session login(Long id, String email, String password) throws UserNotFoundException, InvalidPasswordException {
        //check if user exists
        List<Users> userList = userRepository.findByEmail(email);
        if(userList.isEmpty()) {
            throw new UserNotFoundException("User not found with email: "+email);
        }
        Users user = userList.get(0);
        //check if active token exists
        List<Session> activeSessions = sessionRepository.findByUsersEmailAndSessionStatus(email, SessionStatus.ACTIVE);
        if(!activeSessions.isEmpty()&&activeSessions.get(0).getSessionStatus().equals(SessionStatus.ACTIVE)) {
            return activeSessions.get(0);
        }
        boolean isPasswordMatched = passwordEncoder.matches(password, user.getPassword());
        if(!isPasswordMatched) {
            throw new InvalidPasswordException("Invalid password");
        }
        Session session = new Session();
        session.setToken(generateToken(user));
        session.setUsers(user);
        session.setSessionStatus(SessionStatus.ACTIVE);
        return sessionRepository.save(session);
    }

    @Override
    public String logout(String token) throws InvalidTokenException {
        Optional<Session> session = sessionRepository.findByToken(token);
        if(session.isEmpty()) {
            throw new InvalidTokenException("Invalid token");
        }
        session.get().setSessionStatus(SessionStatus.INACTIVE);
        sessionRepository.save(session.get());
        return token;
    }

    @Override
    public String validateToken(String token) throws InvalidTokenException {
        Optional<Session> session = sessionRepository.findByToken(token);
        if(!(session.isPresent() && session.get().getSessionStatus().equals(SessionStatus.ACTIVE))) {
           throw new InvalidTokenException("Invalid token");
        }
        return token;
    }

    private String generateToken(Users user) {
        return user.getId()+","+user.getEmail()+System.currentTimeMillis();

    }
}
