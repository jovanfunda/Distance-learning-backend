package com.learning.httpMessages.courses;

import com.learning.model.courses.dao.QuestionDAO;

import java.util.List;

public class CreateTestRequest {

    public Long courseID;
    public List<QuestionDAO> questions;
}