package com.learning.controller;

import com.learning.model.users.AppUser;
import com.learning.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/admin")
    public ResponseEntity<List<AppUser>> getAdmins() {
        return ResponseEntity.ok().body(userService.getAdmins());
    }

    @GetMapping("/regularUsers")
    public ResponseEntity<?> getRegularUsers() {
        return ResponseEntity.ok().body(userService.getRegularUsers());
    }

    @PostMapping("/student/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/student/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(appUser));
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<AppUser> getUser(@PathVariable Long userID) {
        return ResponseEntity.ok().body(userService.getUser(userID));
    }

    @PutMapping("/removeAdmin")
    public ResponseEntity<?> removeAdminPermission(@RequestBody String email) {
        return ResponseEntity.ok().body(userService.removeAdminPermission(email));
    }

    @PutMapping("/promoteToAdmin")
    public ResponseEntity<?> promoteToAdmin(@RequestBody String email) {
        return ResponseEntity.ok().body(userService.promoteToAdmin(email));
    }
}