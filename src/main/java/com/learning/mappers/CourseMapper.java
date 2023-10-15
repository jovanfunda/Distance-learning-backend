package com.learning.mappers;

import com.learning.model.courses.Course;
import com.learning.model.courses.dao.CourseDAO;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseDAO toDto(Course entity) {
        if(entity==null)return null;
        CourseDAO dao = new CourseDAO();
        dao.id = entity.getId();
        dao.name = entity.getName();
        if(entity.getOwner() != null)
            dao.fullOwnerName = entity.getOwner().getFirstName() + " " + entity.getOwner().getLastName();
        if(entity.getPictureURL() != null)
            dao.pictureURL = entity.getPictureURL();
        if(entity.getDescription() != null)
            dao.description = entity.getDescription();
        return dao;
    }
}
