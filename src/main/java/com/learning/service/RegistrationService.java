package com.learning.service;

import com.learning.httpMessages.RegistrationRequest;
import com.learning.model.users.ERole;
import com.learning.model.users.AppUser;
import com.learning.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final RoleRepository roleRepository;

    public AppUser registerUser(RegistrationRequest request) {
        return appUserService.signUpUser(new AppUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), roleRepository.findByName(ERole.ROLE_REGULAR).orElseThrow(() -> new RuntimeException("Error: Role is not found."))));
    }

    public AppUser registerAdmin(RegistrationRequest request) {
        return appUserService.signUpUser(new AppUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."))));
    }
}
