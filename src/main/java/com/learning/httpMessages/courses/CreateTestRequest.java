package com.learning.httpMessages.courses;

import com.learning.model.courses.dao.QuestionDAO;

import java.util.Date;
import java.util.List;

public class CreateTestRequest {
    public Long lectureID;
    public List<QuestionDAO> questions;
    public Date startDate;
    public String time;
}
