package com.example.databasemysql.domain.post.service;

import com.example.databasemysql.domain.post.dto.PostDto;
import com.example.databasemysql.domain.post.entity.Post;
import com.example.databasemysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostWriteService {

    private final PostRepository postRepository;

    public Long create(PostDto postDto) {
        var post = Post.builder()
                .memberId(postDto.getMemberId())
                .contents(postDto.getContents())
                .build();

        return postRepository.save(post).getId();
    }


}
