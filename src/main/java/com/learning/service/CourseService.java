package com.learning.service;

import com.learning.configuration.JwtUtils;
import com.learning.exception.CourseAlreadyExistsException;
import com.learning.exception.UserNotFoundException;
import com.learning.exception.CourseNotFoundException;
import com.learning.httpMessages.courses.CourseChangeDataRequest;
import com.learning.httpMessages.courses.CourseOwnershipRequest;
import com.learning.mappers.CourseMapper;
import com.learning.model.courses.Course;
import com.learning.model.courses.Enrollment;
import com.learning.model.courses.dao.CourseDAO;
import com.learning.model.users.AppUser;
import com.learning.repository.AppUserRepository;
import com.learning.repository.CourseRepository;
import com.learning.repository.EnrollmentRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CourseService {

    private CourseRepository courseRepository;
    private AppUserRepository appUserRepository;
    private EnrollmentRepository enrollmentRepository;
    private JwtUtils jwtUtils;
    private CourseMapper courseMapper;

    public List<CourseDAO> getAllCoursesDAO() {
        return courseRepository.findAll().stream().map(courseMapper::toDto).collect(Collectors.toList());
    }

    public List<CourseDAO> getMyCoursesDAO() {
        AppUser user = appUserRepository.findByEmail(jwtUtils.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User " + jwtUtils.getCurrentUsername() + " not found!"));
        List<Long> allCourseIDs = enrollmentRepository.findMyCourses(user.getId());
        List<Course> getAllCourses = courseRepository.findAllById(allCourseIDs);
        return getAllCourses.stream().map(courseMapper::toDto).collect(Collectors.toList());
    }

    public List<CourseDAO> getMyOwnCoursesDAO() {
        AppUser user = appUserRepository.findByEmail(jwtUtils.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException("User " + jwtUtils.getCurrentUsername() + " not found!"));
        List<Course> allCourses = courseRepository.findCoursesByOwnerId(user.getId());
        return allCourses.stream().map(courseMapper::toDto).collect(Collectors.toList());
    }

    public CourseDAO getCourse(Long courseID) {
        Optional<Course> course = courseRepository.findById(courseID);
        if(course.isEmpty())
            throw new CourseNotFoundException(courseID.toString());
        return courseMapper.toDto(courseRepository.findById(courseID).get());
    }

    public Course createCourse(String courseName) {

        if(courseRepository.findByName(courseName).isPresent()) {
            throw new CourseAlreadyExistsException(courseName);
        }

        Course course = new Course();
        course.setName(courseName);
        return courseRepository.save(course);
    }

    public void deleteCourse(Long courseID) {
        Optional<Course> course = courseRepository.findById(courseID);
        course.ifPresent(value -> courseRepository.delete(value));
    }

    public void changeOwnership(CourseOwnershipRequest request) {

        Optional<Course> course = courseRepository.findByName(request.getCourseName());
        Optional<AppUser> newOwner = appUserRepository.findByEmail(request.getNewOwnerEmail());

        if(course.isEmpty() ) {
            throw new CourseNotFoundException(request.getCourseName());
        } else if(newOwner.isEmpty()) {
            throw new UserNotFoundException(request.getNewOwnerEmail());
        }

        course.get().setOwner(newOwner.get());
    }

    public void startEnrollment(String courseName) {

        Optional<Course> course = courseRepository.findByName(courseName);
        Optional<AppUser> user = appUserRepository.findByEmail(jwtUtils.getCurrentUsername());

        if(course.isEmpty()) {
            throw new CourseNotFoundException(courseName);
        } else if(user.isEmpty()) {
            throw new UserNotFoundException(jwtUtils.getCurrentUsername());
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(user.get());
        enrollment.setCourse(course.get());
        enrollment.setScore(0);

        enrollmentRepository.save(enrollment);
    }

    public CourseDAO changeData(CourseChangeDataRequest request) {
        Course course = courseRepository.findById(request.courseID).orElseThrow(() -> new CourseNotFoundException(request.courseID.toString()));
        course.setDescription(request.courseDescription);
        course.setPictureURL(request.coursePictureURL);
        return courseMapper.toDto(course);
    }
}
