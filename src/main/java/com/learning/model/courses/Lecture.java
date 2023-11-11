package com.learning.model.courses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue
    Long id;

    private String title;

    @Column(name="video_url", columnDefinition = "TEXT")
    private String videoURL;

    private String data;

    @OneToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "course_id")
    private Course course;
}
