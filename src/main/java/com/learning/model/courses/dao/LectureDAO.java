package com.learning.model.courses.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LectureDAO {
    public Long id;
    public String title;
    public String videoUrl;
    public String data;
}
