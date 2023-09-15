package com.learning.controller;

import com.learning.httpMessages.courses.CreateTestRequest;
import com.learning.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/create")
    public ResponseEntity<?> createTestForCourse(@RequestBody CreateTestRequest request) {
        return ResponseEntity.ok(testService.saveQuestions(request));
    }
}