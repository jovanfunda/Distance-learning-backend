package com.learning.service;

import com.learning.exception.CourseNotFoundException;
import com.learning.httpMessages.courses.LectureCreationRequest;
import com.learning.mappers.LectureMapper;
import com.learning.model.courses.Course;
import com.learning.model.courses.Lecture;
import com.learning.model.courses.dao.LectureDAO;
import com.learning.repository.CourseRepository;
import com.learning.repository.LectureRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class LectureService {

    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;
    private final LectureMapper lectureMapper;

    public LectureDAO createLecture(LectureCreationRequest request) throws CourseNotFoundException{

        Course course = courseRepository.findByName(request.course).orElseThrow(() -> new CourseNotFoundException("Course with name " + request.course + " not found!"));

        Lecture lecture = new Lecture();
        lecture.setTitle(request.title);
        lecture.setVideoURL(request.videoURL);
        lecture.setData(request.data);
        lecture.setCourse(course);
        return lectureMapper.toDto(lectureRepository.save(lecture));
    }

    public List<LectureDAO> getLecturesByCourseID(Long courseID) {
        courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException("Course with ID " + courseID + " not found!"));
        return lectureRepository.findLectureByCourseID(courseID);
    }
}
