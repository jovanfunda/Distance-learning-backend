package com.learning.mappers;

import com.learning.model.courses.Lecture;
import com.learning.model.courses.dao.LectureDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LectureMapper {

    public LectureDAO toDto(Lecture entity) {
        if(entity==null)return null;
        LectureDAO dao = new LectureDAO();
        dao.id = entity.getId();
        dao.data = entity.getData();
        dao.title = entity.getTitle();
        dao.videoURL = entity.getVideoURL();
        return dao;
    }
}
