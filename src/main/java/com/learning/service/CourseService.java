package com.learning.service;

import com.learning.configuration.JwtUtils;
import com.learning.exception.CourseAlreadyExistsException;
import com.learning.exception.UserNotFoundException;
import com.learning.exception.CourseNotFoundException;
import com.learning.httpMessages.StudentCourseScore;
import com.learning.httpMessages.courses.CourseChangeDataRequest;
import com.learning.httpMessages.courses.CourseOwnershipRequest;
import com.learning.mappers.CourseMapper;
import com.learning.model.courses.Course;
import com.learning.model.courses.Lecture;
import com.learning.model.courses.enrollment.Enrollment;
import com.learning.model.courses.dao.CourseDAO;
import com.learning.model.courses.enrollment.EnrollmentPK;
import com.learning.model.courses.testScore.TestScore;
import com.learning.model.users.AppUser;
import com.learning.repository.AppUserRepository;
import com.learning.repository.CourseRepository;
import com.learning.repository.EnrollmentRepository;
import com.learning.repository.TestScoreRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
    private TestScoreRepository testScoreRepository;
    private JwtUtils jwtUtils;
    private CourseMapper courseMapper;

    public List<CourseDAO> getAllCourses() {
        return courseRepository.findAll().stream().map(courseMapper::toDto).collect(Collectors.toList());
    }

    public List<CourseDAO> getEnrolledCourses() {
        AppUser user = appUserRepository.findById(jwtUtils.getCurrentUsername()).orElseThrow(() -> new UserNotFoundException(jwtUtils.getCurrentUsername()));
        List<Long> allCourseIDs = enrollmentRepository.getEnrolledCourses(user.getEmail());
        List<Course> getAllCourses = courseRepository.findAllById(allCourseIDs);
        return getAllCourses.stream().map(courseMapper::toDto).collect(Collectors.toList());
    }

    public List<String> getEnrolledStudents(Long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID));
        List<String> students = new ArrayList<>();
        for(Enrollment e : course.getEnrollments())
            students.add(e.getStudent().getEmail());
        return students;
    }

    public List<CourseDAO> getMyCourses() {
        AppUser user = appUserRepository.findById(jwtUtils.getCurrentUsername()).orElseThrow(() -> new UserNotFoundException(jwtUtils.getCurrentUsername()));
        return user.getOwnCourses().stream().map(courseMapper::toDto).collect(Collectors.toList());
    }

    public CourseDAO getCourse(Long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID));
        return courseMapper.toDto(course);
    }

    public Course createCourse(String courseName) {
        courseRepository.findByName(courseName).ifPresent(c -> {
            throw new CourseAlreadyExistsException(courseName);
        });

        Course course = new Course();
        course.setName(courseName);
        return courseRepository.save(course);
    }

    public void deleteCourse(Long courseID) {
        courseRepository.deleteById(courseID);
    }

    public void changeOwnership(CourseOwnershipRequest request) {
        Course course = courseRepository.findById(request.courseID).orElseThrow(() -> new CourseNotFoundException(request.courseID));
        AppUser newOwner = appUserRepository.findById(request.newOwnerEmail).orElseThrow(() -> new UserNotFoundException(request.newOwnerEmail));
        course.setOwner(newOwner);
    }

    public void startEnrollment(Long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID));
        AppUser student = appUserRepository.findById(jwtUtils.getCurrentUsername()).orElseThrow(() -> new UserNotFoundException(jwtUtils.getCurrentUsername()));

        EnrollmentPK pk = new EnrollmentPK();
        pk.setCourse(course);
        pk.setStudent(student);

        Enrollment enrollment = new Enrollment();
        enrollment.setId(pk);

        enrollmentRepository.save(enrollment);
    }

    public CourseDAO changeData(CourseChangeDataRequest request) {
        Course course = courseRepository.findById(request.courseID).orElseThrow(() -> new CourseNotFoundException(request.courseID));
        course.setDescription(request.courseDescription);
        course.setPictureURL(request.coursePictureURL);
        return courseMapper.toDto(course);
    }

    public StudentCourseScore getAllScores(String email, Long courseID) {
        appUserRepository.findById(email).orElseThrow(() -> new UserNotFoundException(email));
        Course course = courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID));

        StudentCourseScore scs = new StudentCourseScore();
        scs.setLectureScores(new HashMap<>());

        for(Lecture l : course.getLectures()) {
            if (l.getTest() != null) {
                Optional<TestScore> ts = testScoreRepository.findByStudentEmailAndTestId(email, l.getTest().getId());
                if (ts.isPresent()) {
                    scs.getLectureScores().put(l.getTitle(), ts.get().getScore());
                } else {
                    scs.getLectureScores().put(l.getTitle(), 0);
                }
            }
        }
        return scs;
    }
}
