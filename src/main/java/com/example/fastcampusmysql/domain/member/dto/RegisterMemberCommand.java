package com.example.fastcampusmysql.domain.member.dto;

import java.time.LocalDate;

public record RegisterMemberCommand (
    String email,
    String nick,
    LocalDate birthday
) {
}
