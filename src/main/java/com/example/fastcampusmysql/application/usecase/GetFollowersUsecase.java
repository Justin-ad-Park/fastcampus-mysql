package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetFollowersUsecase {
    private final MemberReadService memberReadService;
    private final FollowReadService followReadService;

    public List<MemberDto> execute(Long fromMemberId) {
        /*
            1. fromMemberId -> Followers
            2. 1번을 순회하면서 회원정보를 찾는다.
         */
        var followers = followReadService.getFollowers(fromMemberId);

        if(followers.isEmpty())
            return List.of();

        //var followerIds = followers.stream().map(f -> f.getToMemberId()).toList();
        var followerIds = followers.stream().map(Follow::getToMemberId).toList();

        return memberReadService.getMembers(followerIds);

    }
}
