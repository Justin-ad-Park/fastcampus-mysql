package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.FastcampusMysqlApplicationTests;
import com.example.fastcampusmysql.domain.post.entity.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
@ComponentScan(
        basePackages = {
                "com.example.fastcampusmysql.domain.post"
        }
)
@EnableAutoConfiguration
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    void findAllByMemberId() {
    }

    /**
     * @Transactional 사용 이유
     *  postRepository의 save는 timeline 처리를 하지 않기 때문에
     *  테스트 후 rollback 되어야 함
      */
    @Test
    @Transactional
    void postSave() {
        var post = Post.builder()
                .memberId(4L)
                .contents("Test Contests")
                .build();

        var savedPost = postRepository.save(post);

        System.out.println(
                String.format(
                        "===Created Post===\n 0%d, 1%d, 2%s"
                        , savedPost.getId(), savedPost.getMemberId(), savedPost.getContents())
        );

        Assertions.assertNotNull(savedPost.getId());
        Assertions.assertEquals(post.getMemberId(), savedPost.getMemberId());
    }

}