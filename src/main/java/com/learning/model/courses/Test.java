package com.learning.model.courses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.model.courses.testScore.TestScore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date testStartDate;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @JsonIgnore
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<TestScore> testScores = new ArrayList<>();
}
