package com.learning.model.courses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue
    public Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "test_id")
    public Test test;

    public String question;
    public String rightAnswer;
    public String wrongAnswer1;
    public String wrongAnswer2;
    public String wrongAnswer3;
}
