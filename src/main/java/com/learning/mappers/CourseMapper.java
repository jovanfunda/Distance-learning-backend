package com.learning.mappers;

import com.learning.model.courses.Course;
import com.learning.model.courses.dao.CourseDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CourseMapper {

    public CourseDAO toDto(Course entity) {
        if(entity==null)return null;
        CourseDAO dao = new CourseDAO();
        dao.name = entity.getName();
        dao.fullOwnerName = entity.getOwner().getFirstName() + " " + entity.getOwner().getLastName();
        dao.pictureURL = entity.getPictureURL();
        dao.description = entity.getDescription();
        return dao;
    }
}
