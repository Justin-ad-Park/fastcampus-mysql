package com.example.databasemysql.domain.member.dto;

import java.time.LocalDate;

public record RegisterMemberCommand (
    String email,
    String nick,
    LocalDate birthday
) {
}
