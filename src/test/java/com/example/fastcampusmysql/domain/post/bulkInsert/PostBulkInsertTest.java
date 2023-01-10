package com.example.fastcampusmysql.domain.post.bulkInsert;

import com.example.fastcampusmysql.FastcampusMysqlApplicationTests;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.PostFixtureFactory;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

/**
 * @Transactional 어노테이션은 테스트 후 롤백 처리를 한다.
 */
//@Transactional
@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
@ComponentScan({ "com.example.fastcampusmysql.domain.post"})
@EnableAutoConfiguration
@Disabled
public class PostBulkInsertTest {
    @Autowired
    private PostRepository postRepository;

    private static StopWatch stopWatch;
    private static final int BULK_INSERT_CNT = 100;

    @BeforeAll
    public static void initTest() {
        stopWatch = new StopWatch();
    }

    @BeforeEach
    public void startWatch() {
        stopWatch.start();
    }


    @AfterEach
    public void stopWatch() {
        stopWatch.stop();

        System.out.println(String.format("메소드 실행 시간 : %s 초" , stopWatch.getTotalTimeSeconds()));
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    @Disabled
    public void bulkInsertOneByOne() {

        EasyRandom easyRandom = getEasyRandom();

        IntStream.range(0, BULK_INSERT_CNT)
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .forEach(x ->
                        postRepository.save(x)
                );

        System.out.println("\n\n****** 대량 등록 - 개별 Insert ******");
    }

    @Test
    @Disabled
    public void bulkInsert() {

        var easyRandom = getEasyRandom();

        var posts = IntStream.range(0, BULK_INSERT_CNT * 100)
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        postRepository.bulkInsert(posts);

        System.out.println("\n\n****** 대량 등록 - 일괄 Insert ******");
    }

    private EasyRandom getEasyRandom() {
        return PostFixtureFactory.get(
                LocalDate.of(1970, 1, 1),
                LocalDate.of(2022, 12, 31)
        );
    }

}
