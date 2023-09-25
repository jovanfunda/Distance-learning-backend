package com.learning.controller;

import com.learning.model.users.AppUser;
import com.learning.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final AppUserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        List<AppUser> users = userService.getUsers();
        if(users.isEmpty()) {
            return new ResponseEntity<>("There are no users in system", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAdmins() {
        List<AppUser> users = userService.getAdmins();
        if(users.isEmpty()) {
            return new ResponseEntity<>("There are no admins in system", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/regularUsers")
    public ResponseEntity<?> getRegularUsers() {
        List<AppUser> users = userService.getRegularUsers();
        if(users.isEmpty()) {
            return new ResponseEntity<>("There are no regular users in system", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/student/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {
        return new ResponseEntity<>(userService.saveUser(appUser), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<AppUser> getUser(@PathVariable Long userID) {
        return new ResponseEntity<>(userService.getUser(userID), HttpStatus.OK);
    }

    @PutMapping("/removeAdmin")
    public ResponseEntity<?> removeAdminPermission(@RequestBody String email) {
        return new ResponseEntity<>(userService.removeAdminPermission(email), HttpStatus.OK);
    }

    @PutMapping("/promoteToAdmin")
    public ResponseEntity<?> promoteToAdmin(@RequestBody String email) {
        return new ResponseEntity<>(userService.promoteToAdmin(email), HttpStatus.OK);
    }
}