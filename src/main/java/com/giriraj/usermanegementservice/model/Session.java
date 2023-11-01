package com.giriraj.usermanegementservice.model;

import com.giriraj.usermanegementservice.dto.LoginResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Session extends BaseModel{
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Users users;
    private String token;
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;

    public LoginResponse convertToLoginResponse() {
        return new LoginResponse(this.token);
    }
}
