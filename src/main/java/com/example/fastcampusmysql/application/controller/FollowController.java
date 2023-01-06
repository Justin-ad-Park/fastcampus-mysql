package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreateFollowMemberUsecase;
import com.example.fastcampusmysql.application.usecase.GetFollowingsUsecase;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {
    final private CreateFollowMemberUsecase createFollowMemberUsecase;
    final private GetFollowingsUsecase getFollowersUsecase;

    @PostMapping("/{fromMemberId}/{toMemberId}")
    public void register(@PathVariable Long fromMemberId, @PathVariable Long toMemberId) {
        createFollowMemberUsecase.execute(fromMemberId, toMemberId);

    }

    @GetMapping("/followers/{fromMemberId}")
    public List<MemberDto> getFollowers(@PathVariable Long fromMemberId) {
        return getFollowersUsecase.execute(fromMemberId);
    }
}
