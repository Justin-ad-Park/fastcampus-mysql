package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.FastcampusMysqlApplicationTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
@ContextConfiguration(locations = "/test-member-context.xml")
@EnableAutoConfiguration
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findById() {

        final var  findMemberId = 4L;
        final var member = memberRepository.findById(findMemberId).get();

        Assertions.assertEquals(findMemberId, member.getId());
    }

    @Test
    void findAllByIdIn() {
    }

    @Test
    void save() {
    }
}