package com.learning.model.courses.dao;

import lombok.Data;

@Data
public class LectureScoringDAO {
    private String lectureTitle;
    private String studentEmail;
    private Integer score;
}
