package com.learning.exception.handler;

import com.learning.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({
            BadCredentialsException.class,
            CantParseJwtException.class
    })
    public ResponseEntity<?> handleAuthExceptions(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            CourseNotFoundException.class,
            QuestionNotFoundException.class
    })
    public ResponseEntity<?> handleNotFoundExceptions(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            CourseAlreadyExistsException.class,
            UserAlreadyExistsException.class
    })
    public ResponseEntity<?> handleConflictExceptions(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
}
