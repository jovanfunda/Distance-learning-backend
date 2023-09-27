package com.learning.service;

import com.learning.configuration.JwtUtils;
import com.learning.exception.CourseNotFoundException;
import com.learning.exception.TestAlreadyExistsException;
import com.learning.httpMessages.SubmitScoreRequest;
import com.learning.httpMessages.courses.CreateTestRequest;
import com.learning.model.courses.Course;
import com.learning.model.courses.Question;
import com.learning.model.courses.Test;
import com.learning.model.courses.dao.QuestionDAO;
import com.learning.model.users.AppUser;
import com.learning.repository.AppUserRepository;
import com.learning.repository.CourseRepository;
import com.learning.repository.QuestionRepository;
import com.learning.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestService {

    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;
    private final TestRepository testRepository;
    private final AppUserRepository appUserRepository;
    private final JwtUtils jwtUtils;

    public Test getTestWithinCourse(Long courseID) {
        courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID.toString()));
        return testRepository.findByCourseId(courseID);
    }

    public Test createTest(CreateTestRequest request) {
        Course course = courseRepository.findById(request.courseID).orElseThrow(() -> new CourseNotFoundException(request.courseID.toString()));
        Optional<Test> testOpt = testRepository.findByName(request.name);

        if(testOpt.isPresent()) {
            if(Objects.equals(testOpt.get().course.getId(), request.courseID))
                throw new TestAlreadyExistsException(request.name);
        }

        Test test = new Test();
        test.setCourse(course);
        test.setName(request.name);
        testRepository.save(test);
        saveQuestions(test, request.questions);

        return test;
    }

    public void saveQuestions(Test test, List<QuestionDAO> questions) {
        for(QuestionDAO q : questions) {
            Question question = new Question();
            question.setTest(test);
            question.setQuestion(q.question);
            question.setRightAnswer(q.rightAnswer);
            question.setWrongAnswer1(q.wrongAnswer1);
            question.setWrongAnswer2(q.wrongAnswer2);
            question.setWrongAnswer3(q.wrongAnswer3);
            questionRepository.save(question);
        }
    }

    public List<Question> getTestQuestions(Long testID) {
        return questionRepository.findQuestionsByTestId(testID);
    }

    public void submitScore(SubmitScoreRequest request) {
        Long courseID = testRepository.findCourseIdByTestId(request.testID);
        courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID.toString()));
        AppUser user = appUserRepository.findByEmail(jwtUtils.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException(jwtUtils.getCurrentUsername()));
        testRepository.submitScore(courseID, request.score, user.getId());
    }
}
