package com.example.javaLang.generic.streamtest.chap12;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.*;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * 다음 업무일을 구한다.
 * 다음 날이 토요일, 일요일인 경우 월요일을 구함.
 */
public class NextWorkingDayOnPages400 implements TemporalAdjuster {

    @Override
    public Temporal adjustInto(Temporal temporal) {
        // 입력된 날짜의 요일 구하기
        DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));

        switch(dow) {
            case FRIDAY:
            case SATURDAY:
            case SUNDAY:
                return temporal.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            default:
                return temporal.plus(1, ChronoUnit.DAYS);
        }
    }

    TemporalAdjuster getNextWorkingDays = temporal -> {
        // 입력된 날짜의 요일 구하기
        DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));

        switch(dow) {
            case FRIDAY:
            case SATURDAY:
            case SUNDAY:
                return temporal.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
            default:
                return temporal.plus(1, ChronoUnit.DAYS);
        }
    };

    @Test
    void test() {
        NextWorkingDayOnPages400 nextWorkingDay = new NextWorkingDayOnPages400();
        LocalDate today = LocalDate.now();
        LocalDate d1 = today.with(new NextWorkingDayOnPages400());
        LocalDate d2 = d1.with(nextWorkingDay);
        LocalDate d3 = d2.with(getNextWorkingDays);
        LocalDate d4 = d3.with(getNextWorkingDays);
        LocalDate d5 = d4.with(getNextWorkingDays);

        var days = Stream.of(today, d1, d2, d3, d4, d5);

        days.forEach(this::printDateAndWeek);

        System.out.println("\n=포맷 비교용=");
        LocalDate day3m1d = LocalDate.of(2023,3,1);
        System.out.println(day3m1d.format(korDFormatter));
        System.out.println(day3m1d.format(korDDFormatter));


    }

    @Test
    void dayOfWeek() {
        // 특정 날짜를 설정합니다.
        LocalDate date = LocalDate.of(2024, 7, 11);

        // Locale을 한국어로 설정하여 요일을 출력합니다.
        Locale koreanLocale = new Locale("ko", "KR");

        // 요일을 한글로 출력합니다.
        String dayOfWeekInKorean = date.getDayOfWeek().getDisplayName(TextStyle.FULL, koreanLocale);

        // 결과 출력
        System.out.println("날짜: " + date);
        System.out.println("요일: " + dayOfWeekInKorean);
    }

    private DateTimeFormatter korDFormatter = DateTimeFormatter.ofPattern("yyyy년 MMM d일(E)", Locale.KOREAN);
    private DateTimeFormatter korDDFormatter = DateTimeFormatter.ofPattern("yyyy년 MMM dd일 E요일", Locale.KOREAN);

    private void printDateAndWeek(LocalDate date) {
        System.out.println(date.format(korDFormatter));
    }

}
