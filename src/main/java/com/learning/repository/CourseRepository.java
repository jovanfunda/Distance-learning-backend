package com.learning.repository;

import com.learning.model.courses.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByName(String name);

    @Query(value = "SELECT e.course_id FROM Enrollment e WHERE e.student_id = :userID", nativeQuery = true)
    List<Long> findMyCourses(@Param("userID") Long userID);
}
