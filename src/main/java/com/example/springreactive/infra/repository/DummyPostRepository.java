package com.example.springreactive.infra.repository;

import com.example.springreactive.domain.entity.Post;
import com.example.springreactive.domain.repository.PostRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DummyPostRepository implements PostRepository {
    private static final Map<Long, Post> DATA = new HashMap<>();
    private static long ID_COUNTER = 1L;

    static {
        Arrays.asList("First Post", "Second Post")
                .forEach((title) -> {
                            long id = ID_COUNTER++;
                            var post = Post.builder()
                                    .id(id)
                                    .title(title)
                                    .content("content of " + title)
                                    .build();
                            DATA.put(id, post);
                        }
                );
    }

    @Override
    public Flux<Post> findAll() {
        return Flux.fromIterable(DATA.values());
    }

    @Override
    public Mono<Post> findById(Long id) {
        if (DATA.containsKey(id) ) {
            return Mono.just(DATA.get(id));
        }

        return Mono.empty();
    }

    @Override
    public Mono<Post> createPost(Post post) {
        long id = ID_COUNTER++;
        post.setId(id);
        DATA.put(id, post);
        return Mono.just(post);
    }
}
