package com.example.fastcampusmysql.domain.timeline.repository;

import com.example.fastcampusmysql.FastcampusMysqlApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
@ComponentScan({ "com.example.fastcampusmysql.domain.timeline"})
@EnableAutoConfiguration
class TimelineRepositoryTest {
    @Autowired
    private TimelineRepository timelineRepository;

    @Test
    void save() {
    }

    @Test
    void findAllByMemberIdByIdDesc() {
        var timelines = timelineRepository.findAllByMemberIdByIdDesc(1L, 10);

        System.out.println("===Timeline PostIds===");
        timelines.stream()
                .forEach(
                        t->System.out.println(t.getPostId())
                );

        Assertions.assertTrue(timelines.size() > 0);
    }

    @Test
    void findAllByLessThanIdAndMemberIdWithOrderByIDDesc() {
    }
}