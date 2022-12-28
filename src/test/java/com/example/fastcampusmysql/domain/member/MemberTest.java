package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MemberTest {

    @DisplayName("닉네임을 변경한다.")
    @Test
    public void testChangeName() {
        final var member = MemberFixtureFactory.create();
        final var expectedNickName = "jsp";

        member.changeNickname(expectedNickName);

        Assertions.assertEquals(expectedNickName, member.getNickname());
    }

    @DisplayName("닉네임은 10자를 초과할 수 없다.")
    @Test
    public void testNicknameMaxLength() {
        final var member = MemberFixtureFactory.create();
        final var expectedNickName = "1234567890A";

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> member.changeNickname(expectedNickName)
                );
    }

}
