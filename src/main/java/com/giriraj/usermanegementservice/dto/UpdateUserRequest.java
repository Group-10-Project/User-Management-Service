package com.giriraj.usermanegementservice.dto;


import com.giriraj.usermanegementservice.model.Users;

public record UpdateUserRequest(String name, String email, String password) {
    public  Users convertToUser() {
        UpdateUserRequest createUserRequest = this;
        return new Users( createUserRequest.name(), createUserRequest.email(), createUserRequest.password());
    }
}
