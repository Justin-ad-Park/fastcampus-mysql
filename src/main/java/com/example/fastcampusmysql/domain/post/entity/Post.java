package com.example.fastcampusmysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Post {
    private final Long id;
    private final Long memberId;
    private final String contents;
    private final LocalDate createdDate;
    private final LocalDateTime createdAt;


    @Builder
    public Post(Long id, Long memberId, String contents, LocalDate createdDate, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.createdDate = Objects.requireNonNullElseGet(createdDate, LocalDate::now);
        this.createdAt = Objects.requireNonNullElseGet(createdAt, LocalDateTime::now);
    }

    public static String toHeaderString()
    {
        return String.format("""
    
    %1$10s\t %2$10s\t %3$-20s\t %4$s
    """, "ID","MemberId","CreatedAt","Contents");
    }


    @Override
    public String toString()
    {
        return String.format("%1$10d\t %2$10d\t %3$-20s\t %4$s", this.id, this.memberId, this.createdAt, this.contents);
    }
}
