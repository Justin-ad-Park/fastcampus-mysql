package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FollowReadService {
    private final FollowRepository followRepository;

    // Following : 내가 구독한 사람
    public List<Follow> getFollowings(Long fromMemberId) {
        return followRepository.findAllByFromMemberId(fromMemberId);
    }

    // Follower : 나를 구독하는 사람
    public List<Follow> getFollowers(Long fromMemberId) {
        return followRepository.findAllByToMemberId(fromMemberId);
    }

}
