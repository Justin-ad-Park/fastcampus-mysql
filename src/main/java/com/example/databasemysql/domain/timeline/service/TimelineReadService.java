package com.example.databasemysql.domain.timeline.service;

import com.example.databasemysql.domain.timeline.entity.Timeline;
import com.example.databasemysql.domain.timeline.repository.TimelineRepository;
import com.example.databasemysql.util.CursorRequestV2;
import com.example.databasemysql.util.PageCursorV2;
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
