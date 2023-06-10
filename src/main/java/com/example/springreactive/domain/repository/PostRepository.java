package com.example.springreactive.domain.repository;

import com.example.springreactive.domain.entity.Post;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository {
    Flux<Post> findAll();

    Mono<Post> findById(Long id);

    Mono<Post> createPost(Post post);
}
