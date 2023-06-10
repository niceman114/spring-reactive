package com.example.springreactive.web.controller;

import com.example.springreactive.domain.entity.Post;
import com.example.springreactive.domain.exception.PostNotFoundException;
import com.example.springreactive.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/posts")
public class PostController {

    private final PostRepository postRepository;

    @GetMapping(value = "")
    public Flux<Post> all() {
        return this.postRepository.findAll().log();
    }

    @GetMapping(value = "/sse/v1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Post> sse() {
        return this.postRepository.findAll().delayElements(Duration.ofSeconds(1)).repeat().log();
    }

    @GetMapping(value = "/sse/v2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Post> sse2() {
        return Flux
                .zip(Flux.interval(Duration.ofSeconds(1)), this.postRepository.findAll().repeat())
                .map(Tuple2::getT2)
                .log();
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
