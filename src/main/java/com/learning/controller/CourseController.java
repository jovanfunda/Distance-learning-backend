package com.learning.controller;

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
    public ResponseEntity<?> getAllCoursesDAO() {
        List<CourseDAO> courses = courseService.getAllCoursesDAO();
        if(courses.isEmpty())
            return new ResponseEntity<>("There are no courses in system", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/myCourses")
    public ResponseEntity<?> getMyCoursesDAO() {
        List<CourseDAO> courses = courseService.getMyCoursesDAO();
        if(courses.isEmpty())
            return new ResponseEntity<>("There are no courses that you enrolled", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/myOwnCourses")
    public ResponseEntity<?> getMyOwnCoursesDAO() {
        List<CourseDAO> courses = courseService.getMyOwnCoursesDAO();
        if(courses.isEmpty())
            return new ResponseEntity<>("There are no courses that you manage", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{courseID}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseID) {
        courseService.deleteCourse(courseID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{courseID}")
    public ResponseEntity<?> getCourse(@PathVariable Long courseID) {
        CourseDAO course = courseService.getCourse(courseID);
        if(course == null)
            return new ResponseEntity<>("No course found with provided ID", HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody String courseName) {
        Course course = courseService.createCourse(courseName);
        if(course == null) {
            return new ResponseEntity<>("Course with same name already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(course, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/changeOwnership")
    public ResponseEntity<?> changeOwnership(@RequestBody CourseOwnershipRequest request) {
        courseService.changeOwnership(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/startEnrollment")
    public ResponseEntity<?> startEnrollment(@RequestBody String courseName) {
        courseService.startEnrollment(courseName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
