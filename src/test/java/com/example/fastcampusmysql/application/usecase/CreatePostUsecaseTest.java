package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.FastcampusMysqlApplicationTests;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.ofType;

@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
@ComponentScan(
        basePackages = {
                "com.example.fastcampusmysql.domain.post",
                "com.example.fastcampusmysql.domain.member",
                "com.example.fastcampusmysql.domain.follow",
                "com.example.fastcampusmysql.domain.timeline"
        },
        basePackageClasses = {CreatePostUsecase.class}
)
@EnableAutoConfiguration
class CreatePostUsecaseTest {
    @Autowired
    private CreatePostUsecase createPostUsecase;

    @Test
    @Transactional
    void createPostUsecase_Test() {
        //when
        final Long MEMBERID = 4L;

        var postDto = getRandomPostDto(MEMBERID).nextObject(PostDto.class);
        var savedPostId = createPostUsecase.execute(postDto);

        System.out.println(
                String.format(
                        "===Created Post===\n 0%d, 1%d, 2%s",
                        savedPostId, postDto.getMemberId(), postDto.getContents()
                )
        );

        Assertions.assertNotNull(savedPostId);
        Assertions.assertEquals(MEMBERID, postDto.getMemberId());
    }


    private EasyRandom getRandomPostDto(Long memberId) {
        var cls = PostDto.class;

        var idPredicate = FieldPredicates.named("id")
                .and(ofType(Long.class))
                .and(inClass(cls));

        var memberIdPredicate = FieldPredicates.named(PostRepository.MEMBER_ID)
                .and(ofType(Long.class))
                .and(inClass(cls));

        var param = new EasyRandomParameters()
                .seed(new Random().nextLong())
                .excludeField(idPredicate)
                .randomize(memberIdPredicate, () -> memberId);

        return new EasyRandom(param);

    }

}