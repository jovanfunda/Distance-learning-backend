package com.learning.service;

import com.learning.configuration.JwtUtils;
import com.learning.exception.UserAlreadyExistsException;
import com.learning.exception.UserNotFoundException;
import com.learning.httpMessages.security.TokenResponse;
import com.learning.model.courses.dao.UserDAO;
import com.learning.model.users.AppUser;
import com.learning.model.users.ERole;
import com.learning.repository.AppUserRepository;
import com.learning.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;

    public AppUser getUser(String email) {
        return appUserRepository.findById(email).orElseThrow(() -> new UserNotFoundException(email));
    }

    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    public List<AppUser> getAdmins() {
        return appUserRepository.findAllAdmins();
    }

    public List<AppUser> getRegularUsers() {
        return appUserRepository.findRegularUsers();
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public TokenResponse generateToken(String email) {

        TokenResponse tokenResponse = new TokenResponse();
        UserDetails userDetails = loadUserByUsername(email);

        tokenResponse.token = jwtUtils.generateToken(userDetails);
        UserDAO user = new UserDAO();

        AppUser appUser = appUserRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException(email));
        user.email = email;
        user.role = appUser.getRole().getName();
        user.fullName = appUser.getFirstName() + " " + appUser.getLastName();

        tokenResponse.user = user;

        return tokenResponse;
    }

    public AppUser removeAdminPermission(String email) {
        AppUser user = appUserRepository.findById(email).orElseThrow(() -> new UserNotFoundException(email));
        user.setRole(roleRepository.findByName(ERole.ROLE_REGULAR).get());
        return user;
    }

    public AppUser promoteToAdmin(String email) {
        AppUser user = appUserRepository.findById(email).orElseThrow(() -> new UserNotFoundException(email));
        user.setRole(roleRepository.findByName(ERole.ROLE_ADMIN).get());
        return user;
    }
}
