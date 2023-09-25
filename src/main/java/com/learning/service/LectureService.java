package com.learning.service;

import com.learning.exception.CourseNotFoundException;
import com.learning.httpMessages.courses.LectureCreationRequest;
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

    public Lecture createLecture(LectureCreationRequest request) throws CourseNotFoundException{

        Course course = courseRepository.findByName(request.course).orElseThrow(() -> new CourseNotFoundException("Course with name " + request.course + " not found!"));

        Lecture lecture = new Lecture();
        lecture.setTitle(request.title);
        lecture.setVideoUrl(request.videoUrl);
        lecture.setData(request.data);
        lecture.setCourse(course);
        return lectureRepository.save(lecture);
    }

    public List<LectureDAO> getLecturesByCourseID(Long courseID) {
        courseRepository.findById(courseID).orElseThrow(() -> new CourseNotFoundException("Course with ID " + courseID + " not found!"));
        return lectureRepository.findLectureByCourseID(courseID);
    }
}
