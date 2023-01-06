package com.example.fastcampusmysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Timeline {
    final private Long id;
    final private Long memberId;
    final private Long postId;
    final private LocalDateTime createdAt;


    @Builder
    public  Timeline(final Long id, final Long memberId, final Long postId, final LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.postId = Objects.requireNonNull(postId);
        this.createdAt = Objects.requireNonNullElseGet(createdAt, LocalDateTime::now) ;
    }
}
