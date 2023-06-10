package com.example.springreactive.domain.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Post(id=" + id +") is not found.");
    }
}
