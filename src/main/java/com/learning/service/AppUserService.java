package com.learning.service;

import com.learning.configuration.AppConfig;
import com.learning.configuration.JwtUtils;
import com.learning.model.users.AppUser;
import com.learning.repository.AppUserRepository;
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

    public void deleteUser(Long userID) {
        appUserRepository.deleteById(userID);
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username with email " + email + " not found"));
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

    public String generateToken(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        return jwtUtils.generateToken(userDetails);
    }
}
