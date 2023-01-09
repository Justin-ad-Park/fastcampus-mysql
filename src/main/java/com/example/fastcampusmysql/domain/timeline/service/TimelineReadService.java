package com.example.fastcampusmysql.domain.timeline.service;

import com.example.fastcampusmysql.domain.timeline.entity.Timeline;
import com.example.fastcampusmysql.domain.timeline.repository.TimelineRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TimelineReadService {
    final TimelineRepository timelineRepository;



    public PageCursor<Timeline> getTimelines(Long memberId, CursorRequest cursorRequest) {
        var timelines = findAllBy(memberId, cursorRequest);
        var minKey = getMinKey(timelines);

        return new PageCursor<>(cursorRequest.next(minKey), timelines);

    }

    private List<Timeline> findAllBy(Long memberId, CursorRequest cursorRequest) {
        List<Timeline> timelines;
        if(cursorRequest.hasKey())
            timelines = timelineRepository.findAllByLessThanIdAndMemberIdWithOrderByIDDesc(cursorRequest.key(), memberId, cursorRequest.size());
        else
            timelines = timelineRepository.findAllByMemberIdByIdDesc( memberId, cursorRequest.size());

        return timelines;
    }


    private long getMinKey(List<Timeline> timelines) {
        return timelines.stream().mapToLong(Timeline::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }
}
