package com.example.fastcampusmysql.domain.union;

import com.example.fastcampusmysql.FastcampusMysqlApplicationTests;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
//@ContextConfiguration(locations = {"/test-member-context.xml","/test-post-context.xml"})
@ComponentScan(basePackages={"com.example.fastcampusmysql.domain.post", "com.example.fastcampusmysql.domain.member"})
//@ContextConfiguration(locations = {"/test-member-context.xml"})
//@ComponentScan(basePackages={"com.example.fastcampusmysql.domain.post"})
@EnableAutoConfiguration
class MemberAndPostRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;

    @Test
    void findById() {

        var findMemberId = 4L;
        var member = memberRepository.findById(findMemberId);

        Assertions.assertEquals(findMemberId, member.get().getId());
    }

    @Test
    void findAllByIdIn() {
    }

    @Test
    void save() {
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
}