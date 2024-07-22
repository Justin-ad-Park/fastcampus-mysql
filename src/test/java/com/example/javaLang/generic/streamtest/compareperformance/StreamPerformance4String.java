package com.example.javaLang.generic.streamtest.compareperformance;

import com.example.stream.stringperformance.StringPerformance;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StreamPerformance4String {

    @Autowired
    StringPerformance stringPerformance;

    static long startTime;

    @BeforeAll
    static void initTime() {
        startTime = System.currentTimeMillis();
    }

    @AfterAll
    static void endTime() {
        long endTime = System.currentTimeMillis();
        System.out.println("Total Time = " + (endTime - startTime) + "ms");
    }

    @Test
    void concatPerformanceTest() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 500; i++)
            result.append(stringPerformance.stringConcat());

    }

    @Test
    void stringJoinerTest() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 500; i++)
            result.append(stringPerformance.stringJoiner());

    }

    @Test
    void stringCollectorTest() {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < 500; i++)
            result.append(stringPerformance.stringCollect());

    }

}
