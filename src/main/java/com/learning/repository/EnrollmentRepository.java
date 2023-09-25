package com.learning.repository;

import com.learning.model.courses.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query(value = "SELECT e.course_id FROM Enrollment e WHERE e.student_id = :userID", nativeQuery = true)
    List<Long> findMyCourses(@Param("userID") Long userID);
}
