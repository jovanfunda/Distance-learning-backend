package com.learning.controller;

import com.learning.model.users.AppUser;
import com.learning.service.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private final AppUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<AppUser>> getAdmins() {
        return new ResponseEntity<>(userService.getAdmins(), HttpStatus.OK);
    }

    @GetMapping("/regularUsers")
    public ResponseEntity<List<AppUser>> getRegularUsers() {
        return new ResponseEntity<>(userService.getRegularUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{userID}")
    public ResponseEntity<AppUser> getUser(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUser(email), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/removeAdmin")
    public ResponseEntity<AppUser> removeAdminPermission(@RequestBody String email) {
        return new ResponseEntity<>(userService.removeAdminPermission(email), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/promoteToAdmin")
    public ResponseEntity<AppUser> promoteToAdmin(@RequestBody String email) {
        return new ResponseEntity<>(userService.promoteToAdmin(email), HttpStatus.OK);
    }
}