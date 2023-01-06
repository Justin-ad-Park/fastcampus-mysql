package com.example.fastcampusmysql.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class PostDto {
    private final Long memberId;
    private final String contents;

    @Builder
    public PostDto(final Long memberId, final String contents) {
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
    }
}
