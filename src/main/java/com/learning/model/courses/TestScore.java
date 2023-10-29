package com.learning.model.courses;

import com.learning.model.courses.Test;
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

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    AppUser student;

    @ManyToOne
    @JoinColumn(name = "test_id")
    Test test;

    int score = 0;

    boolean finished = false;
}
