package com.learning.httpMessages.courses;

import lombok.Data;

@Data
public class FinishedTestResponse {
    boolean finishedTest;
    int score;
}
