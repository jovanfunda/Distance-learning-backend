package com.learning.httpMessages;

import lombok.Data;

import java.util.Map;

@Data
public class StudentCourseScore {
    Map<String, Integer> lectureScores;
}
