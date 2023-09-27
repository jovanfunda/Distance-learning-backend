package com.learning.repository;

import com.learning.model.courses.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findByName(String name);

    Test findByCourseId(Long courseID);

    @Modifying
    @Query("UPDATE Enrollment e SET e.score = :score WHERE e.course.id = :courseID AND e.student.id = :userID")
    void submitScore(@Param("courseID") Long courseID,@Param("score") int score, @Param("userID") Long userID);

    @Query("SELECT t.course.id FROM Test t WHERE t.id = :testID")
    Long findCourseIdByTestId(@Param("testID") Long testID);
}
