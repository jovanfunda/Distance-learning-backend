package com.learning.model.courses.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureDAO {
    public Long id;
    public String title;
    public String videoURL;
    public String data;
}
