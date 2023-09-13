package com.learning.controller;

import com.learning.httpMessages.security.TokenRequest;
import com.learning.httpMessages.security.TokenResponse;
import com.learning.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AppUserService securityService;

    @PostMapping
    @RequestMapping("/login")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody TokenRequest tokenRequest) {
        authenticate(tokenRequest.email, tokenRequest.password);
        TokenResponse tokenResponse = securityService.generateToken(tokenRequest.getEmail());
        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            throw new BadCredentialsException("Wrong username or password", e);
        }
    }
}