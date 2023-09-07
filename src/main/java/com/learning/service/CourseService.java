package com.learning.service;

import com.learning.configuration.JwtUtils;
import com.learning.httpMessages.courses.CourseOwnershipRequest;
import com.learning.model.courses.Course;
import com.learning.model.users.AppUser;
import com.learning.repository.AppUserRepository;
import com.learning.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CourseService {

    private CourseRepository courseRepository;
    private AppUserRepository appUserRepository;
    private JwtUtils jwtUtils;

    public Long createCourse(String courseName) {

        if(courseRepository.findByName(courseName).isPresent()) {
            return null;
        }

        Course course = new Course(courseName);
        course = courseRepository.save(course);
        return course.getId();
    }

    public boolean changeOwnership(CourseOwnershipRequest request) {

        Optional<Course> course = courseRepository.findByName(request.getCourseName());
        Optional<AppUser> newOwner = appUserRepository.findByEmail(request.getNewOwnerEmail());

        if(course.isEmpty()) {
            // error, course not found
            return false;
        } else if(newOwner.isEmpty()) {
            // error, app user not found
            return false;
        }

        course.get().setOwner(newOwner.get());
        return true;
    }

    public boolean startListening(String courseName) {

        Optional<Course> course = courseRepository.findByName(courseName);
        Optional<AppUser> user = appUserRepository.findByEmail(jwtUtils.getCurrentUsername());

        if(course.isEmpty()) {
            // error, course not found
            return false;
        } else if(user.isEmpty()) {
            // error, app user not found
            return false;
        }

        user.get().getCourses().add(course.get());

        return true;
    }
}
