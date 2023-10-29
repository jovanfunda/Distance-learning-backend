package com.learning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LectureNotFoundException extends RuntimeException {

    public LectureNotFoundException(Long id) {
        super("Lecture with ID " + id + " does not exist");
    }
}
