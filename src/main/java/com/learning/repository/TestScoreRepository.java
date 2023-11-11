package com.learning.repository;

import com.learning.model.courses.testScore.TestScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestScoreRepository extends JpaRepository<TestScore, Long> {

    @Modifying
    @Query("UPDATE TestScore ts SET ts.score = :score, ts.finished = true WHERE ts.test.id = :testID AND ts.student.email = :email")
    void submitScore(@Param("testID") Long testID, @Param("score") int score, @Param("email") String email);

    @Query("SELECT ts.finished FROM TestScore ts WHERE ts.test.id = :testID AND ts.student.email = :email")
    boolean didFinishTest(@Param("email") String email, @Param("testID") Long testID);

    @Query("SELECT ts.score FROM TestScore ts WHERE ts.test.id = :testID AND ts.student.email = :email")
    int getScore(@Param("email") String email, @Param("testID") Long testID);

    Optional<TestScore> findByStudentEmailAndTestId(String email, Long testId);
}
