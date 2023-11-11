package com.learning.model.courses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.model.courses.enrollment.Enrollment;
import com.learning.model.users.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "picture_url", columnDefinition = "TEXT")
    private String pictureURL;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_email")
    private AppUser owner;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Lecture> lectures = new ArrayList<>();
}
