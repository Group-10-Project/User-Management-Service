package com.giriraj.usermanegementservice.controller;

import com.giriraj.usermanegementservice.dto.LoginRequest;
import com.giriraj.usermanegementservice.dto.LoginResponse;
import com.giriraj.usermanegementservice.exception.InvalidPasswordException;
import com.giriraj.usermanegementservice.exception.InvalidTokenException;
import com.giriraj.usermanegementservice.exception.UserNotFoundException;
import com.giriraj.usermanegementservice.service.UserAuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-auth")
public class UserAuthenticationController {
    private final UserAuthenticationService userAuthenticationService;

    public UserAuthenticationController(UserAuthenticationService userAuthenticationService) {
        this.userAuthenticationService = userAuthenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws UserNotFoundException, InvalidPasswordException {
        LoginResponse loginResponse = userAuthenticationService.login(loginRequest.id(), loginRequest.email(), loginRequest.password()).convertToLoginResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.add("token", loginResponse.token());
        return ResponseEntity.ok().headers(headers).body(loginResponse);
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("token") String token) throws InvalidTokenException {
        return ResponseEntity.ok().body(userAuthenticationService.logout(token));
    }
    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestHeader("token") String token) throws InvalidTokenException {
        return ResponseEntity.ok().body(userAuthenticationService.validateToken(token));
    }



    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


}
