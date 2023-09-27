package com.learning.controller;

import com.learning.httpMessages.courses.CreateTestRequest;
import com.learning.model.courses.Question;
import com.learning.model.courses.Test;
import com.learning.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping("/create")
    public ResponseEntity<Test> createTestForCourse(@RequestBody CreateTestRequest request) {
        return new ResponseEntity<>(testService.createTest(request), HttpStatus.CREATED);
    }

    @GetMapping("/{courseID}")
    public ResponseEntity<List<Test>> getAllTestsWithinCourse(@PathVariable Long courseID) {
        return new ResponseEntity<>(testService.getAllTestsWithinCourse(courseID), HttpStatus.OK);
    }

    @GetMapping("/questions/{testID}")
    public ResponseEntity<List<Question>> getTestQuestions(@PathVariable Long testID) {
        return new ResponseEntity<>(testService.getTestQuestions(testID), HttpStatus.OK);
    }

}