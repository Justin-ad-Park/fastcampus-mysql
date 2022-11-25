package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class MemberTest {

    @DisplayName("닉네임을 변경한다.")
    @Test
    public void testChangeName() {
        var member = MemberFixtureFactory.create();
        var expectedNickName = "jsp";

        member.changeNickname(expectedNickName);

        Assertions.assertEquals(expectedNickName, member.getNickname());
    }

    @DisplayName("닉네임은 10자를 초과할 수 없다.")
    @Test
    public void testNicknameMaxLength() {
        var member = MemberFixtureFactory.create();
        var expectedNickName = "1234567890A";

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> member.changeNickname(expectedNickName)
                );
    }

}
