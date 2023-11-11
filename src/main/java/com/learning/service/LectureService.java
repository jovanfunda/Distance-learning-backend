package com.learning.service;

import com.learning.configuration.JwtUtils;
import com.learning.exception.CourseNotFoundException;
import com.learning.httpMessages.courses.LectureCreationRequest;
import com.learning.mappers.LectureMapper;
import com.learning.model.courses.Course;
import com.learning.model.courses.Lecture;
import com.learning.model.courses.testScore.TestScore;
import com.learning.model.courses.dao.LectureDAO;
import com.learning.model.courses.dao.LectureResponseDAO;
import com.learning.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class LectureService {

    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;
    private final TestScoreRepository testScoreRepository;
    private final LectureMapper lectureMapper;
    private JwtUtils jwtUtils;

    public LectureDAO createLecture(LectureCreationRequest request) throws CourseNotFoundException{
        Course course = courseRepository.findById(request.courseID).orElseThrow(() -> new CourseNotFoundException(request.courseID));

        Lecture lecture = new Lecture();
        lecture.setTitle(request.title);
        lecture.setVideoURL(request.videoURL);
        lecture.setData(request.data);
        lecture.setCourse(course);
        return lectureMapper.toDto(lectureRepository.save(lecture));
    }

    public List<LectureResponseDAO> getLecturesByCourseID(Long courseID) {
        Course course = courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException(courseID));
        List<Lecture> lectures = course.getLectures();
        List<LectureResponseDAO> responseList = new ArrayList<>();
        for(Lecture item : lectures) {
            LectureResponseDAO lecture = new LectureResponseDAO();
            lecture.setId(item.getId());
            lecture.setTitle(item.getTitle());
            lecture.setVideoURL(item.getVideoURL());
            lecture.setData(item.getData());
            if(item.getTest() != null) {
                lecture.setHasTest(true);
                lecture.setTestStartDate(item.getTest().getTestStartDate());
                Optional<TestScore> ts = testScoreRepository.findByStudentEmailAndTestId(jwtUtils.getCurrentUsername(), item.getTest().getId());
                if(ts.isPresent()) {
                    lecture.setTestFinished(ts.get().isFinished());
                    lecture.setScore(ts.get().getScore());
                }
            } else {
                lecture.setHasTest(false);
                lecture.setTestFinished(false);
                lecture.setTestStartDate(null);
                lecture.setScore(0);
            }
            responseList.add(lecture);
        }
        return responseList;
    }
}
