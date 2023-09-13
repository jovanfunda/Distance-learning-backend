package com.learning.controller;

import com.learning.httpMessages.courses.LectureCreationRequest;
import com.learning.model.courses.dao.LectureDAO;
import com.learning.service.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lecture")
@AllArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping("/create")
    public ResponseEntity<?> createLecture(@RequestBody LectureCreationRequest request) {
        boolean isSuccessful = lectureService.createLecture(request);
        return ResponseEntity.ok(isSuccessful);
    }

    @GetMapping("/{courseID}")
    public ResponseEntity<?> getLecturesByCourseID(@PathVariable Long courseID) {
        List<LectureDAO> lectures = lectureService.getLecturesByCourseID(courseID);
        return ResponseEntity.ok(lectures);
    }
}
