package com.learning.model.courses.dao;

import lombok.Data;

import java.util.Date;

@Data
public class LectureResponseDAO {
    public Long id;
    public String title;
    public String videoURL;
    public String data;
    public boolean hasTest;
    public boolean testFinished;
    public Date testStartDate;
    public int score;
}
