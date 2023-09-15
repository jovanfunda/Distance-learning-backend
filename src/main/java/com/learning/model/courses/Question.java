package com.learning.model.courses;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question {

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    public Course course;

    public String question;
    public String rightAnswer;
    public String wrongAnswer1;
    public String wrongAnswer2;
    public String wrongAnswer3;
}
