package com.giriraj.usermanegementservice.dto;


import com.giriraj.usermanegementservice.model.Users;

public record CreateUserRequest(String name, String email, String password) {
    public  Users convertToUser() {
        CreateUserRequest createUserRequest = this;
        return new Users(createUserRequest.name(), createUserRequest.email(), createUserRequest.password());
    }
}
