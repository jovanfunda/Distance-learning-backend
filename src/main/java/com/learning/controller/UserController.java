package com.learning.controller;

import com.learning.model.users.AppUser;
import com.learning.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final AppUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/student/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/student/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(appUser));
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<AppUser> getUser(@PathVariable Long userID) {
        return ResponseEntity.ok().body(userService.getUser(userID));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/{userID}")
    public ResponseEntity<?> deleteUser(@RequestParam Long userID) {
        userService.deleteUser(userID);
        return ResponseEntity.ok().build();
    }
}