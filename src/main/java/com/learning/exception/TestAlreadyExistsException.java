package com.learning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TestAlreadyExistsException extends RuntimeException {

    public TestAlreadyExistsException(String message) {
        super("Test " + " already exists within this course");
    }
}
