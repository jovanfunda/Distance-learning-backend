package com.learning.httpMessages.courses;

import com.learning.model.courses.dao.QuestionDAO;

import java.util.Date;
import java.util.List;

public class CreateTestRequest {
    public Long courseID;
    public List<QuestionDAO> questions;
    public Date startDate;
    public String time;

    public static long convertTimeToMilliseconds(String time) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        // Calculate the total milliseconds
        long totalMilliseconds = (hours * 60 + minutes) * 60 * 1000;

        return totalMilliseconds;
    }
}
