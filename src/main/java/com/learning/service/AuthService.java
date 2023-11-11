package com.learning.service;

import com.learning.configuration.AppConfig;
import com.learning.exception.RoleNotFoundException;
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

        return appUserService.generateToken(request.email);
    }

    public AppUser register(RegistrationRequest request, ERole role) {
        appUserRepository.findById(request.email).ifPresent(user -> {
            throw new UserAlreadyExistsException(user.getEmail());
        });

        AppUser newUser = new AppUser();
        newUser.setFirstName(request.firstName);
        newUser.setLastName(request.lastName);
        newUser.setEmail(request.email);
        newUser.setPassword(AppConfig.passwordEncoder().encode(request.password));
        newUser.setRole(roleRepository.findByName(role).orElseThrow(() -> new RoleNotFoundException(role.name())));
        appUserRepository.save(newUser);
        return newUser;
    }
}
