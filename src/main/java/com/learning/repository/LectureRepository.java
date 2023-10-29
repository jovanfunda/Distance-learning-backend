package com.learning.repository;

import com.learning.model.courses.Lecture;
import com.learning.model.courses.dao.LectureDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("SELECT NEW com.learning.model.courses.dao.LectureDAO(l.id, l.title, l.videoURL, l.data, l.test.id) FROM Lecture l WHERE l.course.id = :course_id")
    List<LectureDAO> findLectureByCourseID(@Param("course_id") Long course_id);

}
