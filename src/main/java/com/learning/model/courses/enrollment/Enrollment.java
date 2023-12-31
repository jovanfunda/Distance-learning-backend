package com.learning.model.courses.enrollment;

import com.learning.model.courses.Course;
import com.learning.model.users.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

    @EmbeddedId
    private EnrollmentPK id;

    @ManyToOne
    @MapsId("course")
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @MapsId("student")
    @JoinColumn(name = "student_email")
    private AppUser student;
}