package com.learning.model.courses.enrollment;

import com.learning.model.courses.Course;
import com.learning.model.users.AppUser;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class EnrollmentPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_email")
    private AppUser student;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnrollmentPK that = (EnrollmentPK) o;
        return Objects.equals(course.getId(), that.course.getId()) && Objects.equals(student.getEmail(), that.student.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, student);
    }
}