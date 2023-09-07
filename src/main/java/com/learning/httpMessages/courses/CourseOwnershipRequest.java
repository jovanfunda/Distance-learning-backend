package com.learning.httpMessages.courses;

import lombok.Data;

@Data
public class CourseOwnershipRequest {

    private String courseName;
    private String newOwnerEmail;

}
