package com.learning.model.courses;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Test {

    @Id
    @GeneratedValue
    public Long id;

    public String name;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    public Course course;

    @JsonIgnore
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Question> questions;
}
