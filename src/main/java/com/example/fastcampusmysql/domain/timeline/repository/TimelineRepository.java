package com.example.fastcampusmysql.domain.timeline.repository;

import com.example.fastcampusmysql.domain.timeline.entity.Timeline;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimelineRepository {
    private static final String TABLE = "Timeline";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Timeline> TIMELINE_ROW_MAPPER = (ResultSet rs, int rowNum) -> Timeline.builder()
            .id(rs.getLong("id"))
            .memberId(rs.getLong("memberId"))
            .postId(rs.getLong("postId"))
            .createdAt(rs.getObject("createdAt", LocalDateTime.class))
            .build();


    public Timeline save(Timeline timeline) {
        Assert.isNull(timeline.getId(), "Timeline은 업데이트를 지원하지 않습니다.");

        return insert(timeline);
    }

    public List<Timeline> findAllByMemberIdByIdDesc(Long memberId, int size) {
        Assert.isTrue(memberId != null, "MemberId는 필수입니다.");
        Assert.isTrue(size > 0, "Size는 0보다 커야 합니다.");

        var sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId 
                ORDER BY id desc
                LIMIT :size
                """, TABLE);

        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, TIMELINE_ROW_MAPPER);
    }

    public List<Timeline> findAllByLessThanIdAndMemberIdWithOrderByIDDesc(Long id, Long memberId, Long size) {
        var sql = String.format("""
                with covering as (
                select id 
                FROM %1$s
                WHERE memberId = :memberId and id < :id
                ORDER BY id desc
                LIMIT :size
                )
                SELECT *
                FROM %1$s p
                INNER JOIN covering c
                    ON p.id = c.id
                """, TABLE);

        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("id", id)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, TIMELINE_ROW_MAPPER);
    }



    private Timeline insert(Timeline timeline) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(timeline);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Timeline.builder()
                .id(id)
                .memberId(timeline.getMemberId())
                .postId(timeline.getPostId())
                .createdAt(timeline.getCreatedAt())
                .build();
    }

    public void bulkInsert(List<Timeline> timelines) {
        var sql = String.format("""
                INSERT INTO %s (memberId, postId, createdAt) 
                VALUES (:memberId, :postId, :createdAt) 
                """, TABLE);

        SqlParameterSource[] params = timelines
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }


}