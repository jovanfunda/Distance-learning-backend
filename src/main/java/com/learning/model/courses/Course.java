package com.learning.model.courses;

import com.learning.model.users.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Course(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    @ManyToMany(mappedBy = "courses") // Refers to the "courses" field in the Student entity
    private Set<AppUser> listeners = new HashSet<>();

}
