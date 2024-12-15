package com.learning.controller;

import com.learning.httpMessages.CourseScoringDataResponse;
import com.learning.httpMessages.SubmitScoreRequest;
import com.learning.httpMessages.courses.CreateTestRequest;
import com.learning.httpMessages.courses.FinishedTestResponse;
import com.learning.httpMessages.courses.TestStartData;
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
    public ResponseEntity<List<Question>> createTestForLecture(@RequestBody CreateTestRequest request) {
        return new ResponseEntity<>(testService.createTest(request), HttpStatus.CREATED);
    }

    @GetMapping("/hasTest/{lectureID}")
    public ResponseEntity<Boolean> doesLectureHaveTest(@PathVariable Long lectureID) {
        return new ResponseEntity<>(testService.doesLectureHaveTest(lectureID), HttpStatus.OK);
    }

    @GetMapping("/scores/{courseID}")
    public ResponseEntity<CourseScoringDataResponse> getScoringData(@PathVariable Long courseID) {
        return new ResponseEntity<>(testService.getScoringData(courseID), HttpStatus.OK);
    }

    @GetMapping("/{testID}")
    public ResponseEntity<TestStartData> getTestData(@PathVariable Long testID) {
        return new ResponseEntity<>(testService.getTestData(testID), HttpStatus.OK);
    }

    @GetMapping("/questions/{testID}")
    public ResponseEntity<List<Question>> getTestQuestions(@PathVariable Long testID) {
        return new ResponseEntity<>(testService.getTestQuestions(testID), HttpStatus.OK);
    }

    @PutMapping("/submitScore")
    public ResponseEntity<?> submitScore(@RequestBody SubmitScoreRequest request) {
        testService.submitScore(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/didFinishTest/{courseID}")
    public ResponseEntity<FinishedTestResponse> didFinishTest(@PathVariable Long testID) {
        return new ResponseEntity<>(testService.didFinishTest(testID), HttpStatus.OK);
    }

    @DeleteMapping("/{lectureID}")
    public ResponseEntity<Void> deleteTestWithLectureID(@PathVariable Long lectureID) {
        testService.deleteTestWithLectureID(lectureID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}