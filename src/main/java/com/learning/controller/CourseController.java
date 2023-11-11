package com.learning.controller;

import com.learning.httpMessages.StudentCourseScore;
import com.learning.httpMessages.courses.CourseChangeDataRequest;
import com.learning.httpMessages.courses.CourseOwnershipRequest;
import com.learning.model.courses.Course;
import com.learning.model.courses.dao.CourseDAO;
import com.learning.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/course")
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDAO>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/enrolledCourses")
    public ResponseEntity<List<CourseDAO>> getEnrolledCourses() {
        return new ResponseEntity<>(courseService.getEnrolledCourses(), HttpStatus.OK);
    }

    @GetMapping("/myCourses")
    public ResponseEntity<List<CourseDAO>> getMyCourses() {
        return new ResponseEntity<>(courseService.getMyCourses(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{courseID}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseID) {
        courseService.deleteCourse(courseID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{courseID}")
    public ResponseEntity<CourseDAO> getCourse(@PathVariable Long courseID) {
        return new ResponseEntity<>(courseService.getCourse(courseID), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestBody String courseName) {
        return new ResponseEntity<>(courseService.createCourse(courseName), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/changeOwnership")
    public ResponseEntity<Void> changeOwnership(@RequestBody CourseOwnershipRequest request) {
        courseService.changeOwnership(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/startEnrollment")
    public ResponseEntity<Void> startEnrollment(@RequestBody Long courseID) {
        courseService.startEnrollment(courseID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/changeData")
    public ResponseEntity<CourseDAO> changeCourseData(@RequestBody CourseChangeDataRequest request) {
        return new ResponseEntity<>(courseService.changeData(request), HttpStatus.OK);
    }

    @GetMapping("/getEnrolledStudents/{courseID}")
    public ResponseEntity<List<String>> getEnrolledStudents(@PathVariable Long courseID) {
        return new ResponseEntity<>(courseService.getEnrolledStudents(courseID), HttpStatus.OK);
    }

    @GetMapping("/getAllScores")
    public ResponseEntity<StudentCourseScore> getAllScores(@RequestParam String email, @RequestParam Long courseID) {
        return new ResponseEntity<>(courseService.getAllScores(email, courseID), HttpStatus.OK);
    }
}
