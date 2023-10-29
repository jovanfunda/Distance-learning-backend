package com.learning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TestNotFoundException extends RuntimeException {

    public TestNotFoundException(Long id) {
        super("Test with ID " + id + " not found");
    }
}