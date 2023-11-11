package com.learning.service;

import com.learning.configuration.JwtUtils;
import com.learning.exception.CourseNotFoundException;
import com.learning.exception.LectureNotFoundException;
import com.learning.exception.TestNotFoundException;
import com.learning.httpMessages.CourseScoringDataResponse;
import com.learning.httpMessages.SubmitScoreRequest;
import com.learning.httpMessages.courses.CreateTestRequest;
import com.learning.httpMessages.courses.FinishedTestResponse;
import com.learning.httpMessages.courses.StudentDataResponse;
import com.learning.httpMessages.courses.TestStartData;
import com.learning.model.courses.*;
import com.learning.model.courses.dao.LectureScoringDAO;
import com.learning.model.courses.dao.QuestionDAO;
import com.learning.model.courses.enrollment.Enrollment;
import com.learning.model.courses.testScore.TestScore;
import com.learning.model.courses.testScore.TestScorePK;
import com.learning.model.users.AppUser;
import com.learning.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TestService {

    private final QuestionRepository questionRepository;
    private final LectureRepository lectureRepository;
    private final AppUserRepository appUserRepository;
    private final TestRepository testRepository;
    private final TestScoreRepository testScoreRepository;
    private final CourseRepository courseRepository;
    private final JwtUtils jwtUtils;

    public List<Question> createTest(CreateTestRequest request) {
        Lecture lecture = lectureRepository.findById(request.lectureID).orElseThrow(() -> new LectureNotFoundException(request.lectureID));
        request.startDate.setTime(request.startDate.getTime() + convertTimeToMilliseconds(request.time));
        Test test = new Test();
        test.setTestStartDate(request.startDate);
        test.setLecture(lecture);
        lecture.setTest(test);
        testRepository.save(test);
        lectureRepository.save(lecture);
        return saveQuestions(test, request.questions);
    }

    public List<Question> saveQuestions(Test test, List<QuestionDAO> questions) {
        List<Question> quests = new ArrayList<>();
        for(QuestionDAO q : questions) {
            Question question = new Question();
            question.setTest(test);
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

    public List<Question> getTestQuestions(Long testID) {
        return questionRepository.findQuestionsByTestId(testID);
    }

    public void submitScore(SubmitScoreRequest request) {
        AppUser student = appUserRepository.findById(jwtUtils.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException(jwtUtils.getCurrentUsername()));
        Lecture lecture = lectureRepository.findById(request.lectureID).orElseThrow(() -> new LectureNotFoundException(request.lectureID));
        TestScore testScore = new TestScore();
        TestScorePK pk = new TestScorePK();
        pk.setStudent(student);
        pk.setTest(lecture.getTest());
        testScore.setId(pk);
        testScore.setFinished(true);
        testScore.setScore(request.score);
        testScoreRepository.save(testScore);
    }

    public CourseScoringDataResponse getScoringData(Long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID));
        CourseScoringDataResponse response = new CourseScoringDataResponse();
        response.lectures = new ArrayList<>();

        for(Lecture lecture : course.getLectures()) {
            for(Enrollment enrollment : course.getEnrollments()) {
                AppUser student = enrollment.getStudent();

                LectureScoringDAO lectureDAO = new LectureScoringDAO();
                lectureDAO.setLectureTitle(lecture.getTitle());
                lectureDAO.setStudentEmail(student.getEmail());
                if(lecture.getTest() != null) {
                    Optional<TestScore> ts = testScoreRepository.findByStudentEmailAndTestId(student.getEmail(), lecture.getTest().getId());
                    lectureDAO.setScore(null);
                    ts.ifPresent(testScore -> lectureDAO.setScore(testScore.getScore()));

                }
                response.getLectures().add(lectureDAO);
            }
        }

        return response;
    }

    public TestStartData getTestData(Long testID) {
        TestStartData data = new TestStartData();

        data.doesTestExist = testRepository.findById(testID).orElse(null) == null;
        data.startDate = testRepository.findById(testID).orElseThrow(() -> new TestNotFoundException(testID)).getTestStartDate();
        return data;
    }

    public FinishedTestResponse didFinishTest(Long testID) {
        AppUser user = appUserRepository.findById(jwtUtils.getCurrentUsername()).orElseThrow(() -> new UsernameNotFoundException(jwtUtils.getCurrentUsername()));
        testRepository.findById(testID).orElseThrow(() -> new TestNotFoundException(testID));
        FinishedTestResponse response = new FinishedTestResponse();
        response.score = testScoreRepository.getScore(user.getEmail(), testID);
        response.finishedTest = testScoreRepository.didFinishTest(user.getEmail(), testID);
        return response;
    }

    public boolean doesLectureHaveTest(Long lectureID) {
        return lectureRepository.findById(lectureID).orElseThrow(() -> new LectureNotFoundException(lectureID)).getTest() != null;
    }

    public void deleteTestWithLectureID(Long lectureID) {
        Lecture lecture = lectureRepository.findById(lectureID).orElseThrow(() -> new LectureNotFoundException(lectureID));
        testRepository.deleteById(lecture.getTest().getId());
        lecture.setTest(null);
    }

    private static long convertTimeToMilliseconds(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        return (hours * 60L + minutes) * 60 * 1000;
    }
}
