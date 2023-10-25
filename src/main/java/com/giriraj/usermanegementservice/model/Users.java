package com.giriraj.usermanegementservice.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Users extends BaseModel{
    private String name;
    private String email;
    private String password;



}
