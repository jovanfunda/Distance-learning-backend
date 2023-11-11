package com.learning.model.courses.testScore;

import com.learning.model.courses.Course;
import com.learning.model.courses.Test;
import com.learning.model.courses.enrollment.EnrollmentPK;
import com.learning.model.users.AppUser;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class TestScorePK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "student_email")
    private AppUser student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestScorePK that = (TestScorePK) o;
        return Objects.equals(test.getId(), that.test.getId()) && Objects.equals(student.getEmail(), that.student.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(test, student);
    }
}
