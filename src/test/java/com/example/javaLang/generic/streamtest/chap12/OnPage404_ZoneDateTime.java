package com.example.javaLang.generic.streamtest.chap12;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.ThaiBuddhistDate;
import java.util.TimeZone;
import java.util.stream.Stream;

public class OnPage404_ZoneDateTime {

    @Test
    void zoneDateTimeTest() {
        LocalDateTime datetime = LocalDateTime.now();

        ZoneId romeZone = ZoneId.of("Europe/Rome");

        ZonedDateTime zdt1 = datetime.atZone(romeZone);

        System.out.println(datetime);
        System.out.println(zdt1);

    }

    @Test
    void instantNow() {
        ZoneId romeZone = ZoneId.of("Europe/Rome");
        ZoneId bangkokZone = ZoneId.of("Asia/Bangkok");

        Instant instant = Instant.now();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDateTime romeDateTime = LocalDateTime.ofInstant(instant, romeZone);
        LocalDateTime bangkokDateTime = LocalDateTime.ofInstant(instant, bangkokZone);

        System.out.println(localDateTime);
        System.out.println(romeDateTime);
        System.out.println(bangkokDateTime);

        ThaiBuddhistDate thaiBuddhistDate = ThaiBuddhistDate.from(bangkokDateTime);
        System.out.println(thaiBuddhistDate);
    }

    @Test
    void printZoneId() {
        String[] local = TimeZone.getAvailableIDs();

        Stream.of(local).forEach(System.out::println);
    }
}
