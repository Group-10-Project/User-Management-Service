package com.giriraj.usermanegementservice.model;

import com.giriraj.usermanegementservice.dto.LoginResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
public class Session extends BaseModel{
    @ManyToOne//(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Users users;
    private String token;
    @Enumerated(EnumType.STRING)
    private SessionStatus sessionStatus;

    public LoginResponse convertToLoginResponse() {
        return new LoginResponse(this.token);
    }
}
