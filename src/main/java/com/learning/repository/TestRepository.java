package com.learning.repository;

import com.learning.model.courses.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    public Optional<Test> findByLectureId(Long id);
}
