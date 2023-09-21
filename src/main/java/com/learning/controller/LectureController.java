package com.learning.controller;

import com.learning.httpMessages.courses.LectureCreationRequest;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createLecture(@RequestBody LectureCreationRequest request) {
        return ResponseEntity.ok(lectureService.createLecture(request));
    }

    @GetMapping("/{courseID}")
    public ResponseEntity<?> getLecturesByCourseID(@PathVariable Long courseID) {
        List<LectureDAO> lectures = lectureService.getLecturesByCourseID(courseID);
        return ResponseEntity.ok(lectures);
    }
}
