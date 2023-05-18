package com.example.databasemysql.domain.post.repository;

import com.example.databasemysql.FastcampusMysqlApplicationTests;
import com.example.databasemysql.domain.post.entity.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
@ContextConfiguration(locations = "/test-post-context.xml")
@EnableAutoConfiguration
class PostRepositoryTestByContextXML {
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