package com.learning.repository;

import com.learning.model.courses.enrollment.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query(value = "SELECT e.course_id FROM Enrollment e WHERE e.student_id = :userID", nativeQuery = true)
    List<Long> getEnrolledCourses(@Param("userID") Long userID);

    @Modifying
    @Query("UPDATE Enrollment e SET e.score = :score, e.finishedCourse = true WHERE e.course.id = :courseID AND e.student.id = :userID")
    void submitScore(@Param("courseID") Long courseID, @Param("score") int score, @Param("userID") Long userID);

    @Query("SELECT e.finishedCourse FROM Enrollment e WHERE e.course.id = :courseID AND e.student.id = :userID")
    boolean didFinishTest(@Param("userID") Long userID, @Param("courseID") Long courseID);

    @Query("SELECT e.score FROM Enrollment e WHERE e.course.id = :courseID AND e.student.id = :userID")
    int score(@Param("userID") Long userID, @Param("courseID") Long courseID);

    List<Enrollment> getByCourseId(@Param("courseID") Long courseID);
}