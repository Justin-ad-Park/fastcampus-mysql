package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.FastcampusMysqlApplicationTests;
import com.example.fastcampusmysql.domain.post.entity.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
@ComponentScan({ "com.example.fastcampusmysql.domain.post"})
@EnableAutoConfiguration
class PostRepositoryTestByAnnotation {
    @Autowired
    private PostRepository postRepository;

    @Test
    void findAllByMemberId() {
    }


    @Test
    void postSave() {
        var post = Post.builder()
                .memberId(4L)
                .contents("Test Contests")
                .build();

        var savedPost = postRepository.save(post);

        Assertions.assertNotNull(savedPost.getId());
        Assertions.assertEquals(post.getMemberId(), savedPost.getMemberId());

    }

    @Test
    void findAllByMemberIdWithOrderByIDDesc() {
    }
}