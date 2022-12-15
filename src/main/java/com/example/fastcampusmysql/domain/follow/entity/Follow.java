package com.example.fastcampusmysql.domain.follow.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Follow {
    private final Long id;
    private final Long fromMemberId;
    private final Long toMemberId;
    private final LocalDateTime createdAt;


    //생성자
    @Builder
    public Follow(Long id, Long fromMemberId, Long toMemberId, LocalDateTime createdAt) {
        this.id = id;
        this.fromMemberId = Objects.requireNonNull(fromMemberId) ;
        this.toMemberId = Objects.requireNonNull(toMemberId);
        this.createdAt = Objects.requireNonNullElseGet(createdAt, () -> LocalDateTime.now());

    }

    private static void method(Object obj) {
        assert obj != null;
    }
}
