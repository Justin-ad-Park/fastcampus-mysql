package com.example.fastcampusmysql.domain.member.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
//@SpringBootTest(classes = FastcampusMysqlApplicationTests.class)
//@ContextConfiguration(locations = "/test-member-context.xml")
@EnableAutoConfiguration
class MemberRepositoryTestWithoutScan {
    @Autowired
    private MemberRepository memberRepository;

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
}