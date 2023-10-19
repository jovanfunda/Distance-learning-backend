package com.learning.httpMessages.courses;

import lombok.Data;

@Data
public class StudentDataResponse {
    String name;
    String lastName;
    String email;
    int score;
    boolean finishedTest;
}
