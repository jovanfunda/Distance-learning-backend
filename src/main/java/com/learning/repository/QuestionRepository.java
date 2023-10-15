package com.learning.repository;

import com.learning.model.courses.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findQuestionsByCourseId(Long courseID);

    @Modifying
    @Query("DELETE FROM Question q WHERE q.course.id = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);
}
