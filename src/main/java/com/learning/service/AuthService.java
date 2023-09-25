package com.learning.service;

import com.learning.configuration.AppConfig;
import com.learning.exception.UserAlreadyExistsException;
import com.learning.httpMessages.RegistrationRequest;
import com.learning.httpMessages.security.TokenRequest;
import com.learning.httpMessages.security.TokenResponse;
import com.learning.model.users.AppUser;
import com.learning.model.users.ERole;
import com.learning.repository.AppUserRepository;
import com.learning.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    public TokenResponse login(TokenRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email, request.password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return appUserService.generateToken(request.getEmail());
    }

    public AppUser register(RegistrationRequest request, ERole role) {

        if(appUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        String encodedPassword = AppConfig.passwordEncoder().encode(request.getPassword());
        return appUserRepository.save(new AppUser(request.getFirstName(), request.getLastName(), request.getEmail(), encodedPassword, roleRepository.findByName(role).orElseThrow(() -> new RuntimeException("Error: Role is not found."))));
    }
}
