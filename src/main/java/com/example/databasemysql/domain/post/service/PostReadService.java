package com.example.databasemysql.domain.post.service;

import com.example.databasemysql.domain.post.dto.DailyPostCount;
import com.example.databasemysql.domain.post.dto.DailyPostCountRequest;
import com.example.databasemysql.domain.post.entity.Post;
import com.example.databasemysql.domain.post.repository.PostRepository;
import com.example.databasemysql.util.CursorRequestV2;
import com.example.databasemysql.util.PageCursorV2;
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

    public PageCursorV2<Post> getPosts(Long memberId, CursorRequestV2 cursorRequest) {
        var posts = findAllBy(memberId, cursorRequest);

        return new PageCursorV2<>(cursorRequest, posts, Post::getId);

    }

    private List<Post> findAllBy(Long memberId, CursorRequestV2 cursorRequest) {
        List<Post> posts;
        if(cursorRequest.hasKey())
            posts = postRepository.findAllByLessThanIdAndMemberIdWithOrderByIDDesc(cursorRequest.key(), memberId, cursorRequest.size());
        else
            posts = postRepository.findAllByMemberIdWithOrderByIDDesc( memberId, cursorRequest.size());

        return posts;
    }

    public PageCursorV2<Post> getPosts(List<Long> memberIds, CursorRequestV2 cursorRequest) {

        var posts = findAllBy(memberIds, cursorRequest);

        return new PageCursorV2<>(cursorRequest, posts, Post::getId);

    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequestV2 cursorRequest) {
        List<Post> posts;
        if(cursorRequest.hasKey())
            posts = postRepository.findAllByMemberIdsLessThanIdWithOrderByIDDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        else
            posts = postRepository.findAllByMemberIdsWithOrderByIDDesc(memberIds, cursorRequest.size());

        return posts;
    }

    public List<Post> getPostsByPostIds(List<Long> ids) {
        if(ids.size() == 0) return List.of();

        return postRepository.findAllByPostIds(ids);
    }
}
