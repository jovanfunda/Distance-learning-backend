package com.learning.controller;

import com.learning.httpMessages.SubmitScoreRequest;
import com.learning.httpMessages.courses.CreateTestRequest;
import com.learning.httpMessages.courses.FinishedTestResponse;
import com.learning.model.courses.Question;
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
    public ResponseEntity<List<Question>> createTestForCourse(@RequestBody CreateTestRequest request) {
        return new ResponseEntity<>(testService.createTest(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{courseID}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long courseID) {
        testService.deleteTest(courseID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{courseID}")
    public ResponseEntity<Boolean> courseHasTest(@PathVariable Long courseID) {
        return new ResponseEntity<>(testService.hasTest(courseID), HttpStatus.OK);
    }

    @GetMapping("/questions/{courseID}")
    public ResponseEntity<List<Question>> getTestQuestions(@PathVariable Long courseID) {
        return new ResponseEntity<>(testService.getTestQuestions(courseID), HttpStatus.OK);
    }

    @PutMapping("/submitScore")
    public ResponseEntity<?> submitScore(@RequestBody SubmitScoreRequest request) {
        testService.submitScore(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/didFinishTest/{courseID}")
    public ResponseEntity<FinishedTestResponse> didFinishTest(@PathVariable Long courseID) {
        return new ResponseEntity<>(testService.didFinishTest(courseID), HttpStatus.OK);
    }
}