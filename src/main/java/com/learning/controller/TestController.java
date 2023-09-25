package com.learning.controller;

import com.learning.httpMessages.courses.CreateTestRequest;
import com.learning.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/create")
    public ResponseEntity<?> createTestForCourse(@RequestBody CreateTestRequest request) {
        return new ResponseEntity<>(testService.createTest(request), HttpStatus.CREATED);
    }

    @GetMapping("/{testID}")
    public ResponseEntity<?> getTestQuestions(@PathVariable Long testID) {
        return new ResponseEntity<>(testService.getTestQuestions(testID), HttpStatus.OK);
    }

}