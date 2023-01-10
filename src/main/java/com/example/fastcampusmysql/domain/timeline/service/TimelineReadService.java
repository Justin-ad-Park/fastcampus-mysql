package com.example.fastcampusmysql.domain.timeline.service;

import com.example.fastcampusmysql.domain.timeline.entity.Timeline;
import com.example.fastcampusmysql.domain.timeline.repository.TimelineRepository;
import com.example.fastcampusmysql.util.CursorRequestV2;
import com.example.fastcampusmysql.util.PageCursorV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TimelineReadService {
    final TimelineRepository timelineRepository;

    public PageCursorV2<Timeline> getTimelines(Long memberId, CursorRequestV2 cursorRequest) {
        var timelines = findAllBy(memberId, cursorRequest);

        return new PageCursorV2<>(cursorRequest, timelines, Timeline::getId);
    }


    private List<Timeline> findAllBy(Long memberId, CursorRequestV2 cursorRequest) {
        List<Timeline> timelines;
        if(cursorRequest.hasKey())
            timelines = timelineRepository.findAllByLessThanIdAndMemberIdWithOrderByIDDesc(cursorRequest.key(), memberId, cursorRequest.size());
        else
            timelines = timelineRepository.findAllByMemberIdByIdDesc( memberId, cursorRequest.size());

        return timelines;
    }

}
