package com.example.fastcampusmysql.domain.timeline.service;

import com.example.fastcampusmysql.domain.timeline.entity.Timeline;
import com.example.fastcampusmysql.domain.timeline.repository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TimelineWriteService {
    private final TimelineRepository timelineRepository;

    public void deliveryToTimeline(Long postId, List<Long> toMemberIds) {
        var timelines = toMemberIds.stream()
                .map(memberId -> Timeline.builder().memberId(memberId).postId(postId).build())
                .collect(Collectors.toList());

        timelineRepository.bulkInsert(timelines);
    }

}
