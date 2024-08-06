package com.example.javaLang.generic.streamtest.datetime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.util.stream.Collectors;

public class LocalDateStream {
    @Test
    void dateStreamTest() {
        LocalDate startDate = LocalDate.now();

        var dateList = startDate.datesUntil(startDate.plusDays(10), Period.ofDays(2))
                .peek(System.out::println)
                .collect(Collectors.toList());

        System.out.println(dateList);
    }
}
