package com.example.springreactive.web.controller;

import com.example.springreactive.domain.entity.Post;
import com.example.springreactive.domain.exception.PostNotFoundException;
import com.example.springreactive.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private final PostRepository postRepository;

    @GetMapping(value = "")
    public Flux<Post> all() {
        return this.postRepository.findAll().log();
    }

    @GetMapping(value = "/{id}")
    public Mono<Post> get(@PathVariable(value = "id") Long id) {
        return this.postRepository.findById(id).switchIfEmpty(Mono.error(new PostNotFoundException(id)));
    }

    @PostMapping(value = "")
    public Mono<Post> create(Post post) {
        return this.postRepository.createPost(post).log();
    }

}
