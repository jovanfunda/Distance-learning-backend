package com.learning.controller;

import com.learning.httpMessages.courses.CourseOwnershipRequest;
import com.learning.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/course")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<?> getAllCoursesDAO() {
        return ResponseEntity.ok(courseService.getAllCoursesDAO());
    }

    @GetMapping("/myCourses")
    public ResponseEntity<?> getMyCoursesDAO() {
        return ResponseEntity.ok(courseService.getMyCoursesDAO());
    }

    @GetMapping("/myOwnCourses")
    public ResponseEntity<?> getMyOwnCoursesDAO() { return ResponseEntity.ok(courseService.getMyOwnCoursesDAO());}

    @DeleteMapping("/delete/{courseID}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseID) {
        return ResponseEntity.ok(courseService.deleteCourse(courseID));
    }

    @GetMapping("/{courseID}")
    public ResponseEntity<?> getCourse(@PathVariable Long courseID) {
        return ResponseEntity.ok(courseService.getCourse(courseID));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody String courseName) {
        return ResponseEntity.ok(courseService.createCourse(courseName));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/changeOwnership")
    public ResponseEntity<?> changeOwnership(@RequestBody CourseOwnershipRequest request) {
        boolean isSuccessful = courseService.changeOwnership(request);
        return ResponseEntity.ok(isSuccessful);
    }

    @PutMapping("/startEnrollment")
    public ResponseEntity<?> startEnrollment(@RequestBody String courseName) {
        boolean isSuccessful = courseService.startEnrollment(courseName);
        return ResponseEntity.ok(isSuccessful);
    }
}
