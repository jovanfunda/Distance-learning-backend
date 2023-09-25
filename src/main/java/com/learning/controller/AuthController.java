package com.learning.controller;

import com.learning.httpMessages.security.TokenRequest;
import com.learning.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody TokenRequest tokenRequest) {
        return new ResponseEntity<>(authService.login(tokenRequest), HttpStatus.OK);
    }
}