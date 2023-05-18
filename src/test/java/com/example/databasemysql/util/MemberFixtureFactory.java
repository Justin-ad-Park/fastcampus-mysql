package com.example.databasemysql.util;

import com.example.databasemysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFixtureFactory {
    public static Member create() {
        final var param = new EasyRandomParameters();
        return new EasyRandom(param).nextObject(Member.class);
    }

    public static Member create(final Long seed) {
        final var param = new EasyRandomParameters().seed(seed);
        return new EasyRandom(param).nextObject(Member.class);
    }
}
