package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.FastcampusMysqlApplicationTests;
import com.example.fastcampusmysql.application.usecase.timeline.pushmode.GetTimelinePostsUsecaseByPushMode;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.util.CursorRequestV2;
import com.example.fastcampusmysql.util.PageCursorV2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
@ComponentScan(
        basePackages = {
                "com.example.fastcampusmysql.domain.post",
                "com.example.fastcampusmysql.domain.timeline"
        },
        basePackageClasses = {GetTimelinePostsUsecaseByPushMode.class}
)
@EnableAutoConfiguration
public class GetTimelinePostUsecaseByPushModeTest {
    @Autowired
    PostReadService postReadService;

    @Autowired
    GetTimelinePostsUsecaseByPushMode getTimelinePostsUsecaseByPushMode;

    @Test
    void 포스트목록조회() {
        Long[] ids = {5646280L, 5646281L, 5646282L};
        List<Long> postIds = Arrays.asList(ids);
        var posts = postReadService.getPostsByPostIds(postIds);

        System.out.println(Post.toHeaderString());
        posts.stream().forEach(System.out::println);

        Assertions.assertEquals(3, posts.size());
    }

    @Test
    void 타임라인포스트조회() {
        var memberId = 3L;

        CursorRequestV2 cursorRequest = new CursorRequestV2(null, 3L);

        PageCursorV2<Post> posts = getTimelinePostsUsecaseByPushMode.getPostByMemberId(memberId, cursorRequest);
        posts.getBody().forEach(System.out::println);

        System.out.println("NextKey : " + posts.getCursorRequestV2().key());

        Assertions.assertNotNull(posts);
    }

    @Test
    void 타임라인포스트조회_넥스트키없음() {
        var memberId = 3L;

        CursorRequestV2 cursorRequest = new CursorRequestV2(3L, 3L);

        PageCursorV2<Post> posts = getTimelinePostsUsecaseByPushMode.getPostByMemberId(memberId, cursorRequest);

        posts.getBody().forEach(System.out::println);
        System.out.println("NextKey : " + posts.getCursorRequestV2().key());

        Assertions.assertNotNull(posts);
    }

}
