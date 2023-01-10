package com.example.fastcampusmysql.application.usecase.timeline.pushmode;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.timeline.entity.Timeline;
import com.example.fastcampusmysql.domain.timeline.service.TimelineReadService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetTimelinePostsUsecaseByPushMode {
    private final PostReadService postReadService;
    private final TimelineReadService timelineReadService;

    public PageCursor<Post> getPostByMemberId(long memberId, CursorRequest cursorRequest) {
        var timelines = timelineReadService.getTimelines(memberId, cursorRequest);

        var postIds = timelines.body().stream().map(Timeline::getPostId).toList();
        var nextCursorRequest = timelines.nextCursorRequest();
        var posts = postReadService.getPostsByPostIds(postIds);

        return new PageCursor(nextCursorRequest, posts);
    }
}
