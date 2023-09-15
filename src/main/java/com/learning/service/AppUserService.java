package com.learning.service;

import com.learning.configuration.AppConfig;
import com.learning.configuration.JwtUtils;
import com.learning.exception.AppUserNotFoundException;
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

    public AppUser saveUser(AppUser user) {
        return appUserRepository.save(user);
    }

    public AppUser getUser(Long id) {
        return appUserRepository.findById(id).get();
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

    public void deleteUser(Long userID) {
        appUserRepository.deleteById(userID);
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    public AppUser signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();

        if(userExists) {
            throw new IllegalStateException("Email " + appUser.getEmail() + " is already taken");
        }

        String encodedPassword = AppConfig.passwordEncoder().encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        return appUserRepository.save(appUser);
    }

    public TokenResponse generateToken(String email) {

        TokenResponse tokenResponse = new TokenResponse();
        UserDetails userDetails = loadUserByUsername(email);

        tokenResponse.setToken(jwtUtils.generateToken(userDetails));
        UserDAO user = new UserDAO();

        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));
        user.setEmail(email);
        user.setRole(appUser.getRole().getName());
        user.setFullName(appUser.getFirstName() + " " + appUser.getLastName());

        tokenResponse.setUser(user);

        return tokenResponse;
    }

    public boolean removeAdminPermission(String email) {
        AppUser user = appUserRepository.findByEmail(email).orElseThrow(() -> new AppUserNotFoundException("User with email " + email + " not found!"));
        user.setRole(roleRepository.findByName(ERole.valueOf("ROLE_REGULAR")).orElseThrow(() -> new RuntimeException("Role_regular not found!")));
        return true;
    }

    public boolean promoteToAdmin(String email) {
        AppUser user = appUserRepository.findByEmail(email).orElseThrow(() -> new AppUserNotFoundException("User with email " + email + " not found!"));
        user.setRole(roleRepository.findByName(ERole.valueOf("ROLE_ADMIN")).orElse(null));
        return true;
    }
}
