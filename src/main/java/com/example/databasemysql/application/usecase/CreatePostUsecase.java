package com.example.databasemysql.application.usecase;

import com.example.databasemysql.domain.follow.entity.Follow;
import com.example.databasemysql.domain.follow.service.FollowReadService;
import com.example.databasemysql.domain.post.dto.PostDto;
import com.example.databasemysql.domain.post.service.PostWriteService;
import com.example.databasemysql.domain.timeline.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreatePostUsecase {
    final private PostWriteService postWriteService;
    final private FollowReadService followReadService;
    final private TimelineWriteService timelineWriteService;

    @Transactional
    public Long execute(PostDto postDto) {
        var postId = postWriteService.create(postDto);

        var followerMemberIds = followReadService
                .getFollowers(postDto.getMemberId())
                .stream()
                .map(Follow::getFromMemberId)
                .toList();

        timelineWriteService.deliveryToTimeline(postId, followerMemberIds);

        return postId;
    }
}
