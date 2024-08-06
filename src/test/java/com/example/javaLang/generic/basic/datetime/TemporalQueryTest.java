package com.example.javaLang.generic.basic.datetime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TemporalQueryTest {

    @Test
    void test() {
        System.out.println(isItTeaTime());
    }

    private static Boolean isItTeaTime() {
        return LocalDateTime.now().query(
                temporal -> {
//                    var hour = temporal.get(ChronoField.HOUR_OF_DAY);
//                    System.out.println(hour);

                    var time = LocalTime.from(temporal);
                    return time.getHour() >= 16 && time.getHour() <= 17;
                }
        );
    }

    /*
    LocalDate.datesUntil() 메서드는 시작 날짜부터 종료 날짜까지의 날짜를 스트림으로 생성한다.
     */
    @Test
    void dateStreamTest() {
        LocalDate startDate = LocalDate.now();

        startDate.datesUntil(startDate.plusDays(10))
                .forEach(System.out::println);
    }
}
