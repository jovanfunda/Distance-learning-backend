package com.learning.repository;

import com.learning.model.courses.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Optional<Test> findByName(String name);

    List<Test> findByCourseId(Long courseID);
}
