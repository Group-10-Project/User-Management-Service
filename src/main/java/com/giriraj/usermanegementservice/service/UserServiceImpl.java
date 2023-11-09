package com.giriraj.usermanegementservice.service;

import com.giriraj.usermanegementservice.exception.EmailAlreadyExists;
import com.giriraj.usermanegementservice.exception.InvalidTokenException;
import com.giriraj.usermanegementservice.exception.UserNotFoundException;
import com.giriraj.usermanegementservice.model.Users;
import com.giriraj.usermanegementservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserAuthenticationService userAuthenticationService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public UserServiceImpl(UserRepository userRepository, UserAuthenticationService userAuthenticationService) {
        this.userRepository = userRepository;
        this.userAuthenticationService = userAuthenticationService;
    }
    @Override
    public Users createUser(Users user) throws EmailAlreadyExists {
        //check if email already exists
        Users existingUser = null;
        try {
            existingUser = this.getUserByEmail(user.getEmail());
        } catch (UserNotFoundException e) {
            //ignore
        }
        if(existingUser != null) {
            throw new EmailAlreadyExists("Email already exists");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Users getUserById(Long id) throws UserNotFoundException{
        Optional<Users>  user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found with id: "+id);
        }
        return user.get();

    }

    @Override
    public Users getUserByEmail(String email) throws UserNotFoundException {

        List<Users> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found with email: "+email);
        }
        return user.get(0);
    }

    @Override
//    @Transactional
    public String deleteUserById(Long id, String token) throws InvalidTokenException, UserNotFoundException {
        //validate token
//        userAuthenticationService.validateToken(token);
//        if(!getUserIdFromToken(token).equals(id)) {
//            throw new InvalidTokenException("Invalid token");
//        }
//        Optional<Users> user = userRepository.findById(id);
//        if(user.isEmpty()) {
//            throw new UserNotFoundException("User not found with id: "+id);
//        }
//        Optional<Users> u = userRepository.findById(id);
        deleteUser( id);
        return "User deleted successfully";

    }
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Users updateUserById(String token, Long id, Users users) throws InvalidTokenException, UserNotFoundException {
        userAuthenticationService.validateToken(token);
        if(!getUserIdFromToken(token).equals(id)) {
            throw new InvalidTokenException("Invalid token");
        }
        Optional<Users> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException("User not found with id: "+id);
        }
        users.setId(id);
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));
        return userRepository.save(users);
    }

    private Long getUserIdFromToken(String token) {
        return Long.parseLong(token.split(",")[0]);
    }

}
