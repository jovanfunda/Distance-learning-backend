package com.learning.controller;

import com.learning.httpMessages.courses.LectureCreationRequest;
import com.learning.service.LectureService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
