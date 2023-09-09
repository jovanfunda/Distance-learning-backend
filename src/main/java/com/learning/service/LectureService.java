package com.learning.service;

import com.learning.exception.CourseNotFoundException;
import com.learning.httpMessages.courses.LectureCreationRequest;
import com.learning.model.courses.Course;
import com.learning.model.courses.Lecture;
import com.learning.repository.CourseRepository;
import com.learning.repository.LectureRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class LectureService {

    private final LectureRepository lectureRepository;
    private final CourseRepository courseRepository;

    public boolean createLecture(LectureCreationRequest request) {

        Course course = courseRepository.findByName(request.course).orElseThrow(() -> new CourseNotFoundException("Course with name " + request.course + " not found!"));

        Lecture lecture = new Lecture();
        lecture.setTitle(request.title);
        lecture.setVideoUrl(request.videoUrl);
        lecture.setLectureDesc(request.lectureDesc);
        lecture.setCourse(course);
        lectureRepository.save(lecture);
        return true;
    }


}