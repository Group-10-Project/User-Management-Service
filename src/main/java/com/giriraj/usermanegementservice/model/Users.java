package com.giriraj.usermanegementservice.model;

import com.giriraj.usermanegementservice.dto.CreateUserRequest;
import com.giriraj.usermanegementservice.dto.CreateUserResponse;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseModel{
    private String name;
    private String email;
    private String password;

    public  CreateUserResponse convertToCreateUserResponse() {
        Users user = this;
        return new CreateUserResponse(user.getId(), user.getName(), user.getEmail());
    }





}
