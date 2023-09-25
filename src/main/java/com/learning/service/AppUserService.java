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
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;

    public AppUser saveUser(AppUser user) {
        if(appUserRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        return appUserRepository.save(user);
    }

    public AppUser getUser(Long id) {
        Optional<AppUser> user = appUserRepository.findById(id);
        if(user.isEmpty()) {
            throw new UserNotFoundException(id.toString());
        }
        return user.get();
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
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public TokenResponse generateToken(String email) {

        TokenResponse tokenResponse = new TokenResponse();
        UserDetails userDetails = loadUserByUsername(email);

        tokenResponse.setToken(jwtUtils.generateToken(userDetails));
        UserDAO user = new UserDAO();

        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
        user.setEmail(email);
        user.setRole(appUser.getRole().getName());
        user.setFullName(appUser.getFirstName() + " " + appUser.getLastName());

        tokenResponse.setUser(user);

        return tokenResponse;
    }

    public AppUser removeAdminPermission(String email) {
        AppUser user = appUserRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        user.setRole(roleRepository.findByName(ERole.ROLE_REGULAR).get());
        return user;
    }

    public AppUser promoteToAdmin(String email) {
        AppUser user = appUserRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        user.setRole(roleRepository.findByName(ERole.ROLE_ADMIN).get());
        return user;
    }
}
