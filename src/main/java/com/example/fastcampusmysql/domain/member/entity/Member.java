package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {
    private final Long id;

    private String nickname;

    private final String email;

    private final LocalDate birthday;

    private final LocalDateTime createdAt;

    private static final Long NAME_MAX_LENGTH = 10L;

    @Builder
    public Member(final Long id, final String nickname, final String email, final LocalDate birthday, final LocalDateTime createdAt) {
        this.id = id;
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);

        this.validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = Objects.requireNonNullElseGet(createdAt, LocalDateTime::now);
    }

    public void changeNickname(final String toNickname) {
        Objects.requireNonNull(toNickname);
        this.validateNickname(toNickname);
        this.nickname = toNickname;
    }

    private void validateNickname(final String nickname) {
        Assert.isTrue(nickname.length() <= Member.NAME_MAX_LENGTH, "최대 길이를 초과했습니다.");
    }
}
