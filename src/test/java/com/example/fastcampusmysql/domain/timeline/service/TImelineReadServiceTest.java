package com.example.fastcampusmysql.domain.timeline.service;


import com.example.fastcampusmysql.FastcampusMysqlApplicationTests;
import com.example.fastcampusmysql.util.CursorRequestV2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
@ComponentScan(
        basePackages = {
                "com.example.fastcampusmysql.domain.timeline"
        }
)
@EnableAutoConfiguration
public class TImelineReadServiceTest {

        @Autowired
        TimelineReadService timelineReadService;

        @Test
        void 타임라인조회() {
                CursorRequestV2 cr = new CursorRequestV2(null, 3L);

                var timelines= timelineReadService.getTimelines(3L, cr);

                System.out.println("Next Key : " + timelines.getCursorRequestV2().key());
                timelines.getBody().stream().forEach(t-> System.out.println( t.getPostId()));

                Assertions.assertTrue(timelines.getBody().size() > 0);

        }

        @Test
        void 타임라인조회byKey() {
                CursorRequestV2 cr = new CursorRequestV2(14L, 3L);

                var timelines= timelineReadService.getTimelines(3L, cr);

                System.out.println("Next Key : " + timelines.getCursorRequestV2().key());
                timelines.body().stream().forEach(t-> System.out.println( t.getPostId()));

                Assertions.assertTrue(timelines.body().size() > 0);

        }

}
