package com.example.databasemysql.application.usecase.timeline.pushmode;

import com.example.databasemysql.domain.post.entity.Post;
import com.example.databasemysql.domain.post.service.PostReadService;
import com.example.databasemysql.domain.timeline.entity.Timeline;
import com.example.databasemysql.domain.timeline.service.TimelineReadService;
import com.example.databasemysql.util.CursorRequestV2;
import com.example.databasemysql.util.PageCursorV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTimelinePostsUsecaseByPushMode {
    private final PostReadService postReadService;
    private final TimelineReadService timelineReadService;

    public PageCursorV2<Post> getPostByMemberId(long memberId, CursorRequestV2 cursorRequest) {
        var timelines = timelineReadService.getTimelines(memberId, cursorRequest);

        var postIds = timelines.getBody().stream().map(Timeline::getPostId).toList();
        var posts = postReadService.getPostsByPostIds(postIds);

        return new PageCursorV2(timelines.getCursorRequestV2(), posts);
    }
}
