package com.learning.service;

import com.learning.configuration.JwtUtils;
import com.learning.exception.CourseNotFoundException;
import com.learning.httpMessages.SubmitScoreRequest;
import com.learning.httpMessages.courses.CreateTestRequest;
import com.learning.model.courses.Course;
import com.learning.model.courses.Question;
import com.learning.model.courses.dao.QuestionDAO;
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
}
