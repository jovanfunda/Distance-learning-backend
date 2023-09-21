package com.learning.service;

import com.learning.exception.CourseNotFoundException;
import com.learning.httpMessages.courses.CreateTestRequest;
import com.learning.model.courses.Course;
import com.learning.model.courses.Question;
import com.learning.model.courses.Test;
import com.learning.model.courses.dao.QuestionDAO;
import com.learning.repository.CourseRepository;
import com.learning.repository.QuestionRepository;
import com.learning.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestService {

    private final QuestionRepository questionRepository;
    private final CourseRepository courseRepository;
    private final TestRepository testRepository;

    public Test createTest(CreateTestRequest request) {
        Course course = courseRepository.findById(request.courseID).orElseThrow(() -> new CourseNotFoundException("Course with ID " + request.courseID + " not found!"));
        Test test = new Test();
        test.setCourse(course);
        test.setName(request.name);
        testRepository.save(test);

        for(QuestionDAO q : request.questions) {
            Question question = new Question();
            question.setTest(test);
            question.setQuestion(q.question);
            question.setRightAnswer(q.rightAnswer);
            question.setWrongAnswer1(q.wrongAnswer1);
            question.setWrongAnswer2(q.wrongAnswer2);
            question.setWrongAnswer3(q.wrongAnswer3);
            questionRepository.save(question);
        }

        return test;
    }
}
