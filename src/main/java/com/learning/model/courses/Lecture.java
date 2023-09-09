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
    private String videoUrl;

    @Lob
    private String lectureDesc;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
