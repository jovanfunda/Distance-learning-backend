package com.learning.model.courses;

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

    @Column(columnDefinition = "TEXT")
    private String videoUrl;

    private String data;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
