package com.learning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long id) {
        super("Course with ID " + id + " does not exist");
    }

}
