package com.learning.httpMessages;

import com.learning.model.courses.dao.LectureScoringDAO;
import lombok.Data;

import java.util.List;

@Data
public class CourseScoringDataResponse {
    public List<LectureScoringDAO> lectures;
}
