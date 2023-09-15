package com.learning.controller;

import com.learning.httpMessages.RegistrationRequest;
import com.learning.model.users.AppUser;
import com.learning.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public AppUser registerUser(@RequestBody RegistrationRequest request) {
        return registrationService.registerUser(request);
    }

    @PostMapping("/admin")
    public AppUser registerAdmin(@RequestBody RegistrationRequest request) {
        return registrationService.registerAdmin(request);
    }
}
