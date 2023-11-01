package com.giriraj.usermanegementservice.controller;

import com.giriraj.usermanegementservice.dto.CreateUserRequest;
import com.giriraj.usermanegementservice.dto.CreateUserResponse;
import com.giriraj.usermanegementservice.dto.DtoConvertor;
import com.giriraj.usermanegementservice.dto.UpdateUserRequest;
import com.giriraj.usermanegementservice.exception.EmailAlreadyExists;
import com.giriraj.usermanegementservice.exception.InvalidTokenException;
import com.giriraj.usermanegementservice.exception.UserNotFoundException;
import com.giriraj.usermanegementservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/api/v1/user"))
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) throws EmailAlreadyExists {
        return ResponseEntity.ok().body(userService.createUser(createUserRequest.convertToUser()).convertToCreateUserResponse());
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<CreateUserResponse> getUserById(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok().body(userService.getUserById(id).convertToCreateUserResponse());
    }
    //take token in heaeder and userid in path variable and return user details
    @DeleteMapping("/profile/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id,@RequestHeader("token") String token) throws UserNotFoundException, InvalidTokenException {
        return ResponseEntity.ok().body(userService.deleteUserById(id,token));
    }
    @PutMapping("/profile/{id}")
    public ResponseEntity<CreateUserResponse> updateUserById(@RequestHeader("token") String token,@PathVariable Long id,@RequestBody UpdateUserRequest updateUserRequest) throws UserNotFoundException, InvalidTokenException {
        return ResponseEntity.ok().body(userService.updateUserById(token,id,updateUserRequest.convertToUser()).convertToCreateUserResponse());
    }
    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<String> handleEmailAlreadyExists(EmailAlreadyExists e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }





}
