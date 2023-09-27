package com.learning.controller;

import com.learning.httpMessages.courses.LectureCreationRequest;
import com.learning.model.courses.Lecture;
import com.learning.model.courses.dao.LectureDAO;
import com.learning.service.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/lecture")
@AllArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping("/create")
    public ResponseEntity<LectureDAO> createLecture(@RequestBody LectureCreationRequest request) {
        return new ResponseEntity<>(lectureService.createLecture(request), HttpStatus.CREATED);
    }

    @GetMapping("/{courseID}")
    public ResponseEntity<List<LectureDAO>> getLecturesByCourseID(@PathVariable Long courseID) {
        return new ResponseEntity<>(lectureService.getLecturesByCourseID(courseID), HttpStatus.OK);
    }
}
