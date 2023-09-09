package com.learning.service;

import com.learning.configuration.JwtUtils;
import com.learning.httpMessages.courses.CourseOwnershipRequest;
import com.learning.model.courses.Course;
import com.learning.model.courses.Enrollment;
import com.learning.model.users.AppUser;
import com.learning.repository.AppUserRepository;
import com.learning.repository.CourseRepository;
import com.learning.repository.EnrollmentRepository;
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
    private EnrollmentRepository enrollmentRepository;
    private JwtUtils jwtUtils;

    public Long createCourse(String courseName) {

        if(courseRepository.findByName(courseName).isPresent()) {
            return null;
        }

        Course course = new Course();
        course.setName(courseName);
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

    public boolean startEnrollment(String courseName) {

        Course course = courseRepository.findByName(courseName).orElse(null);
        AppUser user = appUserRepository.findByEmail(jwtUtils.getCurrentUsername()).orElse(null);

        if(course == null) {
            // error, course not found
            return false;
        } else if(user == null) {
            // error, app user not found
            return false;
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(user);
        enrollment.setCourse(course);
        enrollment.setScore(0);

        enrollmentRepository.save(enrollment);

        return true;
    }
}
