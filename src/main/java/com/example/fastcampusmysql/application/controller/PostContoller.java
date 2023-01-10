package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreatePostUsecase;
import com.example.fastcampusmysql.application.usecase.timeline.pullmode.GetTimelinePostsUsecaseByPullMode;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.PostSort;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.util.CursorRequestV2;
import com.example.fastcampusmysql.util.PageCursorV2;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostContoller {
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimelinePostsUsecaseByPullMode getTImelinePostsUsecase;
    private final CreatePostUsecase createPostUsecase;

    @PostMapping("")
    public Long create(PostDto postDto) {
        return createPostUsecase.execute(postDto);
    }

    @PostMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(@RequestBody DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<Post> getPosts(
            @PathVariable Long memberId,
            int page,
            int size,
            PostSort postSort
    ) {
        PageRequest pageRequest =  PageRequest.of(page, size, postSort.getSort());
       return postReadService.getPosts(memberId, pageRequest);
    }

    @GetMapping("/members/{memberId}/by-pageable")
    private Page<Post> getPosts(
            @PathVariable Long memberId,
            @Parameter(description = "페이징", example = """
                    {
                      "page": 0,
                      "size": 10,
                      "sort": [
                        "createdDate,DESC"
                      ]
                    }
                    """
                  ) Pageable pageable
    ) {
        return postReadService.getPosts(memberId, pageable);
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursorV2<Post> getPostsByCursor(
            @PathVariable Long memberId,
            CursorRequestV2 cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/members/{memberId}/timeline")
    public PageCursorV2<Post> getTimelineByCursor(
            @PathVariable Long memberId,
            CursorRequestV2 cursorRequest
    ) {
        return getTImelinePostsUsecase.execute(memberId, cursorRequest);
    }

}
