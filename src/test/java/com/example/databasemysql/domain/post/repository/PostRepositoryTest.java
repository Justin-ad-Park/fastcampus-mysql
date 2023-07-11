package com.example.databasemysql.domain.post.repository;

import com.example.databasemysql.FastcampusMysqlApplicationTests;
import com.example.databasemysql.domain.post.entity.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static com.example.databasemysql.domain.post.entity.PostSort.ID_DESC;

@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
@ComponentScan(
        basePackages = {
                "com.example.databasemysql.domain.post"
        }
)
@EnableAutoConfiguration
class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    void findAllByMemberId() {
        PageRequest pageRequest =  PageRequest.of(1, 5, ID_DESC.getSort());
        Page<Post> posts = postRepository.findAllByMemberId(4L, pageRequest);

        posts.stream().forEach(
                p -> System.out.println(
                        String.format("PostId: %1$d, LikeCount:%2$d", p.getId(), p.getLikeCount())
                )
        );

        var post = posts.stream().findAny();
        Assertions.assertNotNull( post.get().getLikeCount());
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
                        "===Created Post===\n %0$d, %1$d, %2$s"
                        , savedPost.getId(), savedPost.getMemberId(), savedPost.getContents())
        );

        Assertions.assertNotNull(savedPost.getId());
        Assertions.assertEquals(post.getMemberId(), savedPost.getMemberId());
    }


}