package com.example.javaLang.generic.streamtest.chap12;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

import static java.time.temporal.TemporalAdjusters.lastInMonth;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

public class TemporalAdjustersTest {
    @Test
    void 오늘포함_이전토요일() {
        LocalDate today = LocalDate.now();
        LocalDate startSaturday = today.with(previousOrSame(DayOfWeek.SATURDAY));
        System.out.println(startSaturday);
    }

    @Test
    void DateTest() {
        LocalDate ld = LocalDate.now();
        LocalDate ld2 = ld.with(lastInMonth(DayOfWeek.SUNDAY));

        System.out.println(ld);
        System.out.println("마지막 일요일 : "  + ld2);
    }

    @Test
    void 오늘포함_오늘요일() {
        LocalDate today = LocalDate.now();
        int todayWeek = today.get(ChronoField.DAY_OF_WEEK);

        DayOfWeek dow = DayOfWeek.of(todayWeek);

        LocalDate previousOrSameDayOfWeek = today.with(previousOrSame(dow));

        Assertions.assertEquals(today,previousOrSameDayOfWeek );

        System.out.println("오늘은 " + previousOrSameDayOfWeek);
        System.out.println("오늘 포함 오늘과 같은 날은 " + previousOrSameDayOfWeek);




    }
}
