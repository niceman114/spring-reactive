package com.example.springreactive.web;

import com.example.springreactive.domain.exception.PostNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.notFound;

@RestControllerAdvice
@Slf4j
public class WebExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Void> postNotFound(PostNotFoundException e) {
        log.warn(e.toString());
        return notFound().build();
    }
}
