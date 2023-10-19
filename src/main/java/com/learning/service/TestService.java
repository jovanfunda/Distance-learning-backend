package com.learning.service;

import com.learning.configuration.JwtUtils;
import com.learning.exception.CourseNotFoundException;
import com.learning.httpMessages.SubmitScoreRequest;
import com.learning.httpMessages.courses.CreateTestRequest;
import com.learning.httpMessages.courses.FinishedTestResponse;
import com.learning.httpMessages.courses.StudentDataResponse;
import com.learning.model.courses.Course;
import com.learning.model.courses.Question;
import com.learning.model.courses.dao.QuestionDAO;
import com.learning.model.courses.enrollment.Enrollment;
import com.learning.model.users.AppUser;
import com.learning.repository.AppUserRepository;
import com.learning.repository.CourseRepository;
import com.learning.repository.EnrollmentRepository;
import com.learning.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TestService {

    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AppUserRepository appUserRepository;
    private final JwtUtils jwtUtils;

    public List<Question> createTest(CreateTestRequest request) {
        Course course = courseRepository.findById(request.courseID).orElseThrow(() -> new CourseNotFoundException(request.courseID.toString()));
        return saveQuestions(course, request.questions);
    }

    public void deleteTest(Long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID.toString()));
        questionRepository.deleteByCourseId(courseID);
    }

    public List<Question> saveQuestions(Course course, List<QuestionDAO> questions) {
        List<Question> quests = new ArrayList<>();
        for(QuestionDAO q : questions) {
            Question question = new Question();
            question.setCourse(course);
            question.setQuestion(q.question);
            question.setRightAnswer(q.rightAnswer);
            question.setWrongAnswer1(q.wrongAnswer1);
            question.setWrongAnswer2(q.wrongAnswer2);
            question.setWrongAnswer3(q.wrongAnswer3);
            questionRepository.save(question);
            quests.add(question);
        }
        return quests;
    }

    public List<Question> getTestQuestions(Long courseID) {
        return questionRepository.findQuestionsByCourseId(courseID);
    }

    public void submitScore(SubmitScoreRequest request) {
        courseRepository.findById(request.courseID).orElseThrow(() -> new CourseNotFoundException(request.courseID.toString()));
        AppUser user = appUserRepository.findByEmail(jwtUtils.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException(jwtUtils.getCurrentUsername()));
        enrollmentRepository.submitScore(request.courseID, request.score, user.getId());
    }

    public boolean hasTest(Long courseID) {
        return questionRepository.findQuestionsByCourseId(courseID).size() > 0;
    }

    public FinishedTestResponse didFinishTest(Long courseID) {
        AppUser user = appUserRepository.findByEmail(jwtUtils.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException(jwtUtils.getCurrentUsername()));
        courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID.toString()));
        FinishedTestResponse response = new FinishedTestResponse();
        response.setScore(enrollmentRepository.score(user.getId(), courseID));
        response.setFinishedTest(enrollmentRepository.didFinishTest(user.getId(), courseID));
        return response;
    }

    public List<StudentDataResponse> getStudentsData(Long courseID) {
        courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID.toString()));
        List<Enrollment> enrollments = enrollmentRepository.getByCourseId(courseID);
        List<StudentDataResponse> data = new ArrayList<>();
        for(Enrollment e : enrollments) {
            StudentDataResponse student = new StudentDataResponse();
            student.setEmail(e.getStudent().getEmail());
            student.setName(e.getStudent().getFirstName());
            student.setLastName(e.getStudent().getLastName());
            student.setScore(e.getScore());
            student.setFinishedTest(e.isFinishedCourse());
            data.add(student);
        }
        return data;
    }
}
