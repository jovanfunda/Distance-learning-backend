package com.learning.model.courses.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDAO {
    public Long id;
    public String name;
    public String fullOwnerName;
    public String pictureURL;
    public String description;
}
