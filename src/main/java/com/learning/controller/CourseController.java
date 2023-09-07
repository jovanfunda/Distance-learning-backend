package com.learning.controller;

import com.learning.httpMessages.courses.CourseOwnershipRequest;
import com.learning.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/course")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody String courseName) {
        Long courseID = courseService.createCourse(courseName);
        return ResponseEntity.created(URI.create("/api/course/" + courseID)).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/changeOwnership")
    public ResponseEntity<?> changeOwnership(@RequestBody CourseOwnershipRequest request) {
        boolean isSuccessful = courseService.changeOwnership(request);
        return ResponseEntity.ok(isSuccessful);
    }

    @PutMapping("/startListening")
    public ResponseEntity<?> startListening(@RequestBody String courseName) {
        boolean isSuccessful = courseService.startListening(courseName);
        return ResponseEntity.ok(isSuccessful);
    }
}
