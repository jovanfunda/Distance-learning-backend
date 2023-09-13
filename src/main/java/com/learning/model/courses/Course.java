package com.learning.model.courses;

import com.learning.model.users.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "picture_url")
    private String pictureURL;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL) // Refers to the "course" field in the Enrollment entity
    private List<Enrollment> enrollment = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lecture> lectures = new ArrayList<>();

}
