package com.example.databasemysql.application.usecase.timeline.pullmode;

import com.example.databasemysql.domain.follow.entity.Follow;
import com.example.databasemysql.domain.follow.service.FollowReadService;
import com.example.databasemysql.domain.post.entity.Post;
import com.example.databasemysql.domain.post.service.PostReadService;
import com.example.databasemysql.util.CursorRequestV2;
import com.example.databasemysql.util.PageCursorV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTimelinePostsUsecaseByPullMode {
    private final  FollowReadService followReadService;
    private final PostReadService postReadService;

    public PageCursorV2<Post> execute(Long memberId, CursorRequestV2 cursorRequest) {
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts (followingMemberIds, cursorRequest);
    }

    public PageCursorV2<Post> executeByTimeline(Long memberId, CursorRequestV2 cursorRequest) {
        /*
            1. Timeline 조회
            2. 1에 해당하는 게스물 조회한다.

         */
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();
        return postReadService.getPosts (followingMemberIds, cursorRequest);
    }

}
