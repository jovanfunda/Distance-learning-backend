package com.learning.controller;

import com.learning.httpMessages.RegistrationRequest;
import com.learning.model.users.AppUser;
import com.learning.model.users.ERole;
import com.learning.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/registration")
@AllArgsConstructor
public class RegistrationController {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<AppUser> registerUser(@RequestBody RegistrationRequest request) {
        return new ResponseEntity<>(authService.register(request, ERole.ROLE_REGULAR), HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppUser> registerAdmin(@RequestBody RegistrationRequest request) {
        return new ResponseEntity<>(authService.register(request, ERole.ROLE_ADMIN), HttpStatus.CREATED);
    }
}
