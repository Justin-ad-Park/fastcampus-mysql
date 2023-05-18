package com.example.databasemysql.util;

import com.example.databasemysql.domain.post.entity.Post;
import com.example.databasemysql.domain.post.repository.PostRepository;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.FieldPredicates;

import java.time.LocalDate;
import java.util.Random;

import static org.jeasy.random.FieldPredicates.inClass;
import static org.jeasy.random.FieldPredicates.ofType;

/**
 * https://github.com/j-easy/easy-random/wiki
 */


public class PostFixtureFactory {

    public static final int MIN_MEMBER_ID = 1;
    public static final int MAX_MEMBER_ID = 10+1;

    public static EasyRandom get(LocalDate firstDate, LocalDate lastDate) {
        var r = new Random();

        var idPredicate = FieldPredicates.named("id")
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        var memberIdPredicate = FieldPredicates.named(PostRepository.MEMBER_ID)
                .and(ofType(Long.class))
                .and(inClass(Post.class));

        var param = new EasyRandomParameters()
                .excludeField(idPredicate)
                .dateRange(firstDate, lastDate)
                .randomize(memberIdPredicate, () -> {
                    return r.nextLong(MIN_MEMBER_ID, MAX_MEMBER_ID);
                });

        return new EasyRandom(param);

    }
}
