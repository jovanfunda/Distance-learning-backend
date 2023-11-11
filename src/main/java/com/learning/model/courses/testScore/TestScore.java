package com.learning.model.courses.testScore;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.model.courses.Test;
import com.learning.model.courses.enrollment.EnrollmentPK;
import com.learning.model.users.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TestScore {

    @EmbeddedId
    private TestScorePK id;

    @ManyToOne
    @MapsId("course")
    @JoinColumn(name = "student_email")
    AppUser student;

    @ManyToOne
    @MapsId("course")
    @JoinColumn(name = "test_id")
    Test test;

    int score = 0;

    boolean finished = false;
}
