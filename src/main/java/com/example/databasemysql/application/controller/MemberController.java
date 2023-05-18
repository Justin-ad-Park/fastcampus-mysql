package com.example.databasemysql.application.controller;

import com.example.databasemysql.domain.member.dto.MemberDto;
import com.example.databasemysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.databasemysql.domain.member.dto.RegisterMemberCommand;
import com.example.databasemysql.domain.member.service.MemberReadService;
import com.example.databasemysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    @PostMapping
    public MemberDto register(@RequestBody final RegisterMemberCommand command) {
        final var member = this.memberWriteService.create(command);

        return this.memberReadService.toDto(member);
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable final Long id) {
        return this.memberReadService.getMember(id);
    }

    @PutMapping("/{id}")
    public MemberDto changeNickname(@PathVariable final Long id, @RequestBody final String nickname) {
        final var member = this.memberWriteService.changeNickname(id, nickname);
        return this.memberReadService.toDto(member);
    }

    @GetMapping("/memberNicknameHistories/{memberId}")
    public List<MemberNicknameHistoryDto> getMemberNicknameHistory(@PathVariable final Long memberId) {
        return this.memberReadService.getMemberNicknameHistories(memberId);
    }

}

