package com.example.fastcampusmysql.application.usecase.timeline.pushmode;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.timeline.entity.Timeline;
import com.example.fastcampusmysql.domain.timeline.service.TimelineReadService;
import com.example.fastcampusmysql.util.CursorRequestV2;
import com.example.fastcampusmysql.util.PageCursorV2;
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
