package com.learning.mappers;

import com.learning.model.courses.Lecture;
import com.learning.model.courses.dao.LectureDAO;
import org.springframework.stereotype.Component;

@Component
public class LectureMapper {

    public LectureDAO toDto(Lecture entity) {
        if(entity==null)return null;
        LectureDAO dao = new LectureDAO();
        dao.id = entity.getId();
        dao.data = entity.getData();
        dao.title = entity.getTitle();
        dao.videoURL = entity.getVideoURL();
        if(entity.getTest() != null)
            dao.testID = entity.getTest().getId();
        return dao;
    }
}
