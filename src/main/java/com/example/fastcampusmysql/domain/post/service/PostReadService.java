package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {
    final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest dailyPostCountRequest) {
        /*
            반환 값 -> 리스트 [작성일자, 작성회원, 작성 게시물 갯수]
            select * from Post
            where memberId = :memberId
                and createdDate between :firstDate and :lastDate
            group by createdDate, memberId
         */
        return postRepository.groupByCreatedDate(dailyPostCountRequest);

    }

    public Page<Post> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllByMemberId(memberId, pageable);
    }

    public PageCursor<Post> getPosts(Long memberId, CursorRequest cursorRequest) {
        var posts = findAllBy(memberId, cursorRequest);
        var minKey = getMinKey(posts);

        return new PageCursor<>(cursorRequest.next(minKey), posts);

    }

    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        List<Post> posts;
        if(cursorRequest.hasKey())
            posts = postRepository.findAllByLessThanIdAndMemberIdWithOrderByIDDesc(cursorRequest.key(), memberId, cursorRequest.size());
        else
            posts = postRepository.findAllByMemberIdWithOrderByIDDesc( memberId, cursorRequest.size());

        return posts;
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {

        var posts = findAllBy(memberIds, cursorRequest);
        var minKey = getMinKey(posts);

        return new PageCursor<>(cursorRequest.next(minKey), posts);

    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        List<Post> posts;
        if(cursorRequest.hasKey())
            posts = postRepository.findAllByMemberIdsLessThanIdWithOrderByIDDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        else
            posts = postRepository.findAllByMemberIdsWithOrderByIDDesc(memberIds, cursorRequest.size());

        return posts;
    }

    private long getMinKey(List<Post> posts) {
        return posts.stream().mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }

    public List<Post> getPostsByPostIds(List<Long> ids) {
        if(ids.size() == 0) return List.of();


        return postRepository.findAllByPostIds(ids);
    }
}
