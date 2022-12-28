package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.util.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {
    private static final String TABLE = "post";
    public static final String MEMBER_ID = "memberId";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final RowMapper<DailyPostCount> DAILY_POST_COUNT_MAPPER = (ResultSet rs, int rowNum) ->
            new DailyPostCount(
                    rs.getLong(MEMBER_ID),
                    rs.getObject("createdDate", LocalDate.class),
                    rs.getLong("count")
            );
    private static final RowMapper<Post> POST_ROW_MAPPER;

    static {
        POST_ROW_MAPPER = (ResultSet rs, int rowNum) -> Post.builder()
                .id(rs.getLong("id"))
                .memberId(rs.getLong(MEMBER_ID))
                .contents(rs.getString("contents"))
                .createdDate(rs.getObject("createdDate", LocalDate.class))
                .createdAt(rs.getObject("createdAt", LocalDateTime.class))
                .build();

    }

    public List<DailyPostCount> groupByCreatedDate(DailyPostCountRequest dailyPostCountRequest) {
        var sql = String.format("""
                SELECT createdDate, memberId, count(id) count
                FROM %s
                WHERE memberId = :memberId and createdDate between :firstDate and :lastDate
                GROUP BY memberId, createdDate
                """, TABLE);

        var params = new BeanPropertySqlParameterSource(dailyPostCountRequest);

        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_MAPPER);
    }

    public Page<Post> findAllByMemberId(Long memberId, Pageable pageable) {
        var params = new MapSqlParameterSource()
                .addValue(MEMBER_ID, memberId)
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        var sql = String.format("""
                SELECT * 
                FROM %s 
                WHERE memberId = :memberId
                ORDER BY %s
                LIMIT :size
                OFFSET :offset
                """, TABLE, PageHelper.orderBy(pageable.getSort()));

        var posts = namedParameterJdbcTemplate.query(sql, params,POST_ROW_MAPPER );

        return new PageImpl(posts, pageable, getCount(memberId));
    }

    private Long getCount(Long memberId) {
        var sql = String.format("""
                SELECT count(id)
                FROM %s
                WHERE memberId = :memberId
                """, TABLE);

        var params = new MapSqlParameterSource().addValue(MEMBER_ID, memberId);

        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);

    }

    public Post save(Post post) {
        //if(post.getId() != null) throw new IllegalArgumentException("Post는 업데이트를 지원하지 않습니다.");
        Assert.isNull(post.getId(), "Post는 업데이트를 지원하지 않습니다.");

        return insert(post);
    }

    public void bulkInsert(List<Post> posts) {
        var sql = String.format("""
                INSERT INTO %s (memberId, contents, createdDate, createdAt)
                VALUES (:memberId, :contents, :createdDate, :createdAt)
                """, TABLE);

        SqlParameterSource[] params = posts
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }

    private Post insert(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public List<Post> findAllByMemberIdWithOrderByIDDesc(Long memberId, Long size) {
        var sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId = :memberId
                ORDER BY id desc
                LIMIT :size
                """, TABLE);

        var params = new MapSqlParameterSource().addValue(MEMBER_ID, memberId)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, POST_ROW_MAPPER);
    }

    public List<Post> findAllByLessThanIdAndMemberIdWithOrderByIDDesc(Long id, Long memberId, Long size) {
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

        var params = new MapSqlParameterSource().addValue(MEMBER_ID, memberId)
                .addValue("id", id)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, POST_ROW_MAPPER);
    }

    public List<Post> findAllByMemberIdsWithOrderByIDDesc(List<Long> memberIds, Long size) {
        var sql = String.format("""
                SELECT *
                FROM %s
                WHERE memberId in (:memberIds)
                ORDER BY id desc
                LIMIT :size
                """, TABLE);

        var params = new MapSqlParameterSource().addValue(MEMBER_ID, memberIds)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, POST_ROW_MAPPER);
    }

    public List<Post> findAllByMemberIdsLessThanIdWithOrderByIDDesc(Long id, List<Long> memberIds, Long size) {
        var sql = String.format("""
                with covering as (
                select id 
                FROM %1$s
                WHERE memberId in (:memberIds) and id < :id
                ORDER BY id desc
                LIMIT :size
                )
                SELECT *
                FROM %1$s p
                INNER JOIN covering c
                    ON p.id = c.id
                """, TABLE);

        var params = new MapSqlParameterSource().addValue(MEMBER_ID, memberIds)
                .addValue("id", id)
                .addValue("size", size);

        return namedParameterJdbcTemplate.query(sql, params, POST_ROW_MAPPER);
    }
}
